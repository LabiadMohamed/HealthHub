package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.EmailSubscriptionResponse;
import com.healthhub.healthhub_backend.entity.EmailSubscription;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.repository.EmailSubscriptionRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailSubscriptionService {

    private final EmailSubscriptionRepository emailSubscriptionRepository;
    private final UserRepository userRepository;

    public EmailSubscriptionService(EmailSubscriptionRepository emailSubscriptionRepository,
                                    UserRepository userRepository) {
        this.emailSubscriptionRepository = emailSubscriptionRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private EmailSubscriptionResponse toResponse(EmailSubscription sub) {
        return new EmailSubscriptionResponse(
                sub.getId(), sub.getEmail(),
                sub.isActive(), sub.getSubscribedAt()
        );
    }

    // User — subscribe
    public EmailSubscriptionResponse subscribe() {
        User user = getCurrentUser();

        if (emailSubscriptionRepository.existsByUserId(user.getId())) {
            // Reactivate if exists
            EmailSubscription sub = emailSubscriptionRepository.findByUserId(user.getId())
                    .orElseThrow(() -> new RuntimeException("Subscription not found"));
            sub.setActive(true);
            return toResponse(emailSubscriptionRepository.save(sub));
        }

        EmailSubscription sub = new EmailSubscription();
        sub.setUser(user);
        sub.setEmail(user.getEmail());
        sub.setActive(true);

        return toResponse(emailSubscriptionRepository.save(sub));
    }

    // User — unsubscribe
    public void unsubscribe() {
        User user = getCurrentUser();
        EmailSubscription sub = emailSubscriptionRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        sub.setActive(false);
        emailSubscriptionRepository.save(sub);
    }

    // Admin — get all active subscribers
    public List<EmailSubscriptionResponse> getAllActive() {
        return emailSubscriptionRepository.findByIsActiveTrue()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }
}