package com.aimentor.ai_mentor.learning_path;


import java.time.LocalDateTime;
import java.util.UUID;

import com.aimentor.ai_mentor.learning_goal.LearningGoal;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter 
@Setter  
@Table(name = "learning_path")
public class LearningPath {

    @Id 
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @OneToOne
    @JoinColumn(name = "goal_id", nullable = false, unique = true)
    private LearningGoal goal;

    @Column (name = "plan",nullable = false)
    private String plan;

    @Column (name = "finished_steps")
    private int finishedSteps;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @PrePersist 
    private void time(){
        this.createdAt = LocalDateTime.now();
    }

}
