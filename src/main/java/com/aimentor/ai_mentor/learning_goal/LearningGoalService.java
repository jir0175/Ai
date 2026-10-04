package com.aimentor.ai_mentor.learning_goal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.aimentor.ai_mentor.learning_goal.DTO.RequestCreate;
import com.aimentor.ai_mentor.learning_goal.DTO.ResponseCreateGoal;
import com.aimentor.ai_mentor.user.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class LearningGoalService {

private final LearningGoalsRepository learningGoalsRepository;

//creat goal
public ResponseCreateGoal createGoal(RequestCreate newGoal){
    
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
    User user = (User) authentication.getPrincipal();
    LearningGoal goal = new LearningGoal();
    goal.setSubject(newGoal.getSubject());
    goal.setHourPerDay(newGoal.getHourPerDay());
    goal.setDeadline(newGoal.getDeadline());
    goal.setLevel(newGoal.getLevel());
    goal.setUser(user);
    learningGoalsRepository.save(goal);
    ResponseCreateGoal createNewGoal = new ResponseCreateGoal();
    
    createNewGoal.setSubject(goal.getSubject());
    createNewGoal.setHourPerDay(goal.getHourPerDay());
    createNewGoal.setDeadline(goal.getDeadline());
    createNewGoal.setLevel(goal.getLevel());
    
    return createNewGoal;
}
}
