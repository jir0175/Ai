package com.aimentor.ai_mentor.learning_path;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


public interface LearningPathRepository extends JpaRepository<LearningPath,UUID>{
     Optional<LearningPath> findByGoal_Id(UUID goalId);
     boolean existsByGoal_Id(UUID goalId);    
}
