package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.EmailSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmailSubscriptionRepository extends JpaRepository<EmailSubscription, Long> {

    Optional<EmailSubscription> findByUserId(Long userId);

    List<EmailSubscription> findByIsActiveTrue();

    boolean existsByUserId(Long userId);
}