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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class LearningPathService {
    private final LearningPathRepository learningPathRepository;
    private final LearningGoalsRepository learningGoalsRepository;

    //create plan
    public ResponseCreatePlan createPlan(UUID goalId,String plan){
        LearningGoal goal = learningGoalsRepository.findById(goalId).orElseThrow(() -> new GoalNotFoundException("Learning goal not found"));
        Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        boolean correctUser = user.getId().equals(goal.getUser().getId());
        boolean planExists = learningPathRepository.existsByGoal_Id(goalId);
        if(correctUser && !planExists){
            
                LearningPath path = new LearningPath();
                path.setPlan(plan);
                path.setGoal(goal);
                learningPathRepository.save(path);
                ResponseCreatePlan createPlan = new ResponseCreatePlan();
                createPlan.setPlan(path.getPlan());
                createPlan.setFinishedSteps(path.getFinishedSteps());
                return createPlan;
            }else if(planExists && correctUser){
                throw new LearningPathAlreadyExistsException("A learning path already exists for this goal");
            }else{
                throw new GoalAccessException("You do not have access to this goal");
            }
            
    }
}
