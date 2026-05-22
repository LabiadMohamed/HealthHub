package com.healthhub.healthhub_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "email_subscriptions")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String email;

    @Column(nullable = false)
    private boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime subscribedAt = LocalDateTime.now();

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
}