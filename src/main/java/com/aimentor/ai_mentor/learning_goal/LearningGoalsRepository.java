package com.aimentor.ai_mentor.learning_goal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface LearningGoalsRepository extends JpaRepository<LearningGoal, UUID> {

}
