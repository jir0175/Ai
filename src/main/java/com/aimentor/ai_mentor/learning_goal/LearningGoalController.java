package com.aimentor.ai_mentor.learning_goal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aimentor.ai_mentor.learning_goal.DTO.RequestCreate;
import com.aimentor.ai_mentor.learning_goal.DTO.ResponseCreateGoal;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor 
@RestController 
@RequestMapping("/api/goals")
public class LearningGoalController {
    private final LearningGoalService learningGoalService;
    @PostMapping
    public ResponseCreateGoal postCreateGoal(@RequestBody @Valid RequestCreate newGoal) {
        return (learningGoalService.createGoal(newGoal));
    }
    @GetMapping("/{id}")
    public ResponseCreateGoal getGoalById(@PathVariable  UUID id){
        return learningGoalService.findGoalById(id);
    }
}