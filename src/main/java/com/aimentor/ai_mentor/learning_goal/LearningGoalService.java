package com.aimentor.ai_mentor.learning_goal;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor 
public class LearningGoalService {

private final LearningGoalsRepository learningGoalsRepository;

//creat goal
public ResponseCreateGoal createGoal(RequestCreateGoal newGoal){
    Authentication authentication =
        SecurityContextHolder.getContext().getAuthentication();
    LearningGoal goal = new LearningGoal();
    goal.setSubject(newGoal.getSubject());
    goal.setHourPerDay(newGoal.getHourPerDay());
    goal.setDeadline(newGoal.getDeadline());
    goal.setLevel(newGoal.getLevel());
    goal.setUser(newGoal.getUser());
}
}
