package com.aimentor.ai_mentor.learning_goal.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.aimentor.ai_mentor.learning_goal.LearningLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.Getter;

import lombok.Setter;

@Getter 
@Setter 

public class RequestCreate {
   
    @NotBlank
    
    private String subject;
    @NotNull 
    private LearningLevel level;
    @NotNull
    private BigDecimal hourPerDay;
    @NotNull
    private LocalDate deadline;

    
    
}