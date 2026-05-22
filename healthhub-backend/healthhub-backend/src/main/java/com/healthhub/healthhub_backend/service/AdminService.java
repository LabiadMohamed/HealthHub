package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.AdminStatsResponse;
import com.healthhub.healthhub_backend.dto.UserResponse;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.enums.CampaignStatus;
import com.healthhub.healthhub_backend.enums.DonationStatus;
import com.healthhub.healthhub_backend.enums.Role;
import com.healthhub.healthhub_backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ArticleRepository articleRepository;
    private final DonationRepository donationRepository;
    private final CampaignRepository campaignRepository;
    private final EmailSubscriptionRepository emailSubscriptionRepository;

    public AdminService(UserRepository userRepository,
                        BookRepository bookRepository,
                        ArticleRepository articleRepository,
                        DonationRepository donationRepository,
                        CampaignRepository campaignRepository,
                        EmailSubscriptionRepository emailSubscriptionRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.articleRepository = articleRepository;
        this.donationRepository = donationRepository;
        this.campaignRepository = campaignRepository;
        this.emailSubscriptionRepository = emailSubscriptionRepository;
    }

    // Platform statistics
    public AdminStatsResponse getStats() {
        return new AdminStatsResponse(
                userRepository.count(),
                bookRepository.count(),
                articleRepository.count(),
                donationRepository.count(),
                donationRepository.findByStatus(DonationStatus.OPEN).size(),
                campaignRepository.count(),
                campaignRepository.findByStatus(CampaignStatus.ACTIVE).size(),
                emailSubscriptionRepository.findByIsActiveTrue().size()
        );
    }

    // Get all users
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toUserResponse)
                .collect(Collectors.toList());
    }

    // Promote user to volunteer
    public void promoteToVolunteer(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.VOLUNTEER);
        userRepository.save(user);
    }

    // Promote user to admin
    public void promoteToAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(Role.ADMIN);
        userRepository.save(user);
    }

    // Delete user
    public void deleteUser(Long userId) {
        if (!userRepository.existsById(userId)) {
            throw new RuntimeException("User not found");
        }
        userRepository.deleteById(userId);
    }

    private UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name(),
                user.isVolunteer(),
                user.isDonor(),
                user.getCreatedAt()
        );
    }
}