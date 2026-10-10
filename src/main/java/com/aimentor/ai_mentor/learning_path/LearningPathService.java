package com.aimentor.ai_mentor.learning_path;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.aimentor.ai_mentor.learning_goal.LearningGoal;
import com.aimentor.ai_mentor.learning_goal.LearningGoalsRepository;
import com.aimentor.ai_mentor.learning_goal.exception.GoalNotFoundException;
import com.aimentor.ai_mentor.learning_path.DTO.ResponseCreatePlan;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class LearningPathService {
    private final LearningPathRepository learningPathRepository;
    private final LearningGoalsRepository learningGoalsRepository;

    //create plan
    public ResponseCreatePlan createPlan(UUID goalId,String plan){
        LearningGoal goal = learningGoalsRepository.findById(goalId).orElseThrow(() -> new GoalNotFoundException("Learning goal not found"));
        if(learningPathRepository.existsByGoal_Id(goalId)){
            throw new RuntimeException("we have this plan");
        }else{
            LearningPath path = new LearningPath();
            path.setPlan(plan);
            path.setGoal(goal);
            learningPathRepository.save(path);
            ResponseCreatePlan createPlan = new ResponseCreatePlan();
            createPlan.setPlan(path.getPlan());
            createPlan.setFinishedSteps(path.getFinishedSteps());
            return createPlan;
        }
    }
}
