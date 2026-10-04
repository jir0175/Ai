package com.aimentor.ai_mentor.learning_goal.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.aimentor.ai_mentor.learning_goal.LearningLevel;

import lombok.Getter;

import lombok.Setter;

@Getter 
@Setter 

public class ResponseCreateGoal {
    
    private String subject;
    private LearningLevel level;
    private BigDecimal hourPerDay;
    private LocalDate deadline;

   
}
