package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.NotificationRequest;
import com.healthhub.healthhub_backend.dto.NotificationResponse;
import com.healthhub.healthhub_backend.entity.Notification;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.repository.NotificationRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private NotificationResponse toResponse(Notification n) {
        return new NotificationResponse(
                n.getId(), n.getTitle(), n.getMessage(),
                n.getType(), n.isRead(), n.getCreatedAt()
        );
    }

    // Admin — send notification to a specific user
    public NotificationResponse send(NotificationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Notification notification = new Notification();
        notification.setUser(user);
        notification.setTitle(request.getTitle());
        notification.setMessage(request.getMessage());
        notification.setType(request.getType());

        return toResponse(notificationRepository.save(notification));
    }

    // Get my notifications
    public List<NotificationResponse> getMyNotifications() {
        User user = getCurrentUser();
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Get my unread notifications
    public List<NotificationResponse> getMyUnread() {
        User user = getCurrentUser();
        return notificationRepository.findByUserIdAndIsReadFalse(user.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Count unread
    public long countUnread() {
        User user = getCurrentUser();
        return notificationRepository.countByUserIdAndIsReadFalse(user.getId());
    }

    // Mark one as read
    public void markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }
}