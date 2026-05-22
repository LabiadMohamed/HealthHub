package com.healthhub.healthhub_backend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public class AuthRequest {

    @NotBlank(message = "Name is required")
    private String name;
    @Email(message = "Invalid Email")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
