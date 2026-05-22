package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.EmailSubscriptionResponse;
import com.healthhub.healthhub_backend.service.EmailSubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@CrossOrigin(origins = "*")
public class EmailSubscriptionController {

    private final EmailSubscriptionService emailSubscriptionService;

    public EmailSubscriptionController(EmailSubscriptionService emailSubscriptionService) {
        this.emailSubscriptionService = emailSubscriptionService;
    }

    @PostMapping("/subscribe")
    public ResponseEntity<EmailSubscriptionResponse> subscribe() {
        return ResponseEntity.ok(emailSubscriptionService.subscribe());
    }

    @PatchMapping("/unsubscribe")
    public ResponseEntity<Void> unsubscribe() {
        emailSubscriptionService.unsubscribe();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EmailSubscriptionResponse>> getAllActive() {
        return ResponseEntity.ok(emailSubscriptionService.getAllActive());
    }
}