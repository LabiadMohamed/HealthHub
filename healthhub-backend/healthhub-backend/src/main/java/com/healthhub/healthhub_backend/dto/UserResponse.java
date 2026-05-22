package com.healthhub.healthhub_backend.dto;

import java.time.LocalDateTime;

public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
    private boolean isVolunteer;
    private boolean isDonor;
    private LocalDateTime createdAt;

    public UserResponse(Long id, String name, String email, String role,
                        boolean isVolunteer, boolean isDonor, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.isVolunteer = isVolunteer;
        this.isDonor = isDonor;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
    public boolean isVolunteer() { return isVolunteer; }
    public boolean isDonor() { return isDonor; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}