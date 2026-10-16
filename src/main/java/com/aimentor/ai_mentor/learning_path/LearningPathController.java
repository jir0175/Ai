package com.aimentor.ai_mentor.learning_path;

import org.springframework.web.bind.annotation.RestController;

import com.aimentor.ai_mentor.learning_path.DTO.ResponseCreatePlan;



import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/path")
@RequiredArgsConstructor  
@RestController 
public class LearningPathController {
    private final LearningPathService learningPathService;
    
    @PostMapping("/{id}")
    public ResponseCreatePlan createPlan(@PathVariable UUID id, @RequestBody String path) {
        return learningPathService.createPlan(id,path);
        
        
    }
    
}
