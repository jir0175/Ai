package com.aimentor.ai_mentor.learning_goal;

import com.aimentor.ai_mentor.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name="learning_goal")
public class LearningGoal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "subject",nullable = false)
    private String subject;
    @Enumerated(EnumType.STRING)
    @Column(name = "level", nullable = false)
    private LearningLevel level;

    @Column(name="hour_per_day",nullable = false)
    private BigDecimal hourPerDay;

    @Column(name="deadline",nullable = false)
    private LocalDate deadline;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

}
