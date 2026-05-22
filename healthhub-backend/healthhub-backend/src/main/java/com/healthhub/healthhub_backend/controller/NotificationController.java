package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.NotificationRequest;
import com.healthhub.healthhub_backend.dto.NotificationResponse;
import com.healthhub.healthhub_backend.service.NotificationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    // Admin — send notification
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NotificationResponse> send(@Valid @RequestBody NotificationRequest request) {
        return ResponseEntity.ok(notificationService.send(request));
    }

    // Get my notifications
    @GetMapping
    public ResponseEntity<List<NotificationResponse>> getMy() {
        return ResponseEntity.ok(notificationService.getMyNotifications());
    }

    // Get my unread
    @GetMapping("/unread")
    public ResponseEntity<List<NotificationResponse>> getUnread() {
        return ResponseEntity.ok(notificationService.getMyUnread());
    }

    // Count unread
    @GetMapping("/unread/count")
    public ResponseEntity<Long> countUnread() {
        return ResponseEntity.ok(notificationService.countUnread());
    }

    // Mark as read
    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.noContent().build();
    }
}