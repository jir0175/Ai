package com.aimentor.ai_mentor.learning_path;

import java.util.UUID;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aimentor.ai_mentor.learning_goal.LearningGoal;
import com.aimentor.ai_mentor.learning_goal.LearningGoalsRepository;
import com.aimentor.ai_mentor.learning_goal.exception.GoalAccessException;
import com.aimentor.ai_mentor.learning_goal.exception.GoalNotFoundException;
import com.aimentor.ai_mentor.learning_path.DTO.ResponseCreatePlan;
import com.aimentor.ai_mentor.learning_path.exception.LearningPathAlreadyExistsException;
import com.aimentor.ai_mentor.user.User;
import com.aimentor.ai_mentor.ai.OpenRouterService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class LearningPathService {
    private final LearningPathRepository learningPathRepository;
    private final LearningGoalsRepository learningGoalsRepository;
    private final OpenRouterService openRouterService;
    //create plan
    public ResponseCreatePlan createPlan(UUID goalId) {
    LearningGoal goal = learningGoalsRepository.findById(goalId)
            .orElseThrow(() -> new GoalNotFoundException("Learning goal not found"));

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    User user = (User) authentication.getPrincipal();

    boolean correctUser = user.getId().equals(goal.getUser().getId());

    if (!correctUser) {
        throw new GoalAccessException("You do not have access to this goal");
    }

    boolean planExists = learningPathRepository.existsByGoal_Id(goalId);

    if (planExists) {
        throw new LearningPathAlreadyExistsException(
                "A learning path already exists for this goal"
        );
    }

    String prompt = """
            Create a practical study plan in Russian.

            Subject: %s
            Current level: %s
            Available study time per day: %s hours
            Deadline: %s

            Requirements:
            - Divide the plan into clear stages.
            - Include concrete topics and practical exercises.
            - Make the workload realistic for the available daily study time.
            - Organize the plan according to the deadline.
            - Return only the study plan, without an introduction.
            """.formatted(
                    goal.getSubject(),
                    goal.getLevel(),
                    goal.getHourPerDay(),
                    goal.getDeadline()
            );

    String plan = openRouterService.ask(prompt);

    LearningPath path = new LearningPath();
    path.setPlan(plan);
    path.setGoal(goal);

    learningPathRepository.save(path);

    ResponseCreatePlan response = new ResponseCreatePlan();
    response.setPlan(path.getPlan());
    response.setFinishedSteps(path.getFinishedSteps());

    return response;
}}
