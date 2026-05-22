package com.healthhub.healthhub_backend.dto;

import java.time.LocalDateTime;

public class EmailSubscriptionResponse {

    private Long id;
    private String email;
    private boolean isActive;
    private LocalDateTime subscribedAt;

    public EmailSubscriptionResponse(Long id, String email, boolean isActive,
                                     LocalDateTime subscribedAt) {
        this.id = id;
        this.email = email;
        this.isActive = isActive;
        this.subscribedAt = subscribedAt;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public boolean isActive() { return isActive; }
    public LocalDateTime getSubscribedAt() { return subscribedAt; }
}