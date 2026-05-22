package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.DonationRequest;
import com.healthhub.healthhub_backend.dto.DonationResponse;
import com.healthhub.healthhub_backend.entity.Donation;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.enums.DonationStatus;
import com.healthhub.healthhub_backend.repository.DonationRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationService {

    private final DonationRepository donationRepository;
    private final UserRepository userRepository;

    public DonationService(DonationRepository donationRepository,
                           UserRepository userRepository) {
        this.donationRepository = donationRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private DonationResponse toResponse(Donation donation) {
        String donorName = donation.getDonor() != null ? donation.getDonor().getName() : null;
        return new DonationResponse(
                donation.getId(),
                donation.getType(),
                donation.getBloodType(),
                donation.getMedicationName(),
                donation.getAmount(),
                donation.getCurrency(),
                donation.getStatus(),
                donation.getNotes(),
                donation.getRequester().getName(),
                donorName,
                donation.getCreatedAt()
        );
    }

    // Create a donation request
    public DonationResponse createDonation(DonationRequest request) {
        User user = getCurrentUser();

        Donation donation = new Donation();
        donation.setRequester(user);
        donation.setType(request.getType());
        donation.setBloodType(request.getBloodType());
        donation.setMedicationName(request.getMedicationName());
        donation.setAmount(request.getAmount());
        donation.setCurrency(request.getCurrency());
        donation.setNotes(request.getNotes());
        donation.setStatus(DonationStatus.OPEN);

        return toResponse(donationRepository.save(donation));
    }

    // Respond to a donation (become the donor)
    public DonationResponse respondToDonation(Long donationId) {
        User user = getCurrentUser();

        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));

        if (donation.getStatus() != DonationStatus.OPEN) {
            throw new RuntimeException("Donation is no longer open");
        }

        donation.setDonor(user);
        donation.setStatus(DonationStatus.FULFILLED);

        return toResponse(donationRepository.save(donation));
    }

    // Admin — update status manually
    public DonationResponse updateStatus(Long donationId, DonationStatus status) {
        Donation donation = donationRepository.findById(donationId)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
        donation.setStatus(status);
        return toResponse(donationRepository.save(donation));
    }

    // Get all open donations
    public List<DonationResponse> getOpenDonations() {
        return donationRepository.findByStatus(DonationStatus.OPEN)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Get my donation requests
    public List<DonationResponse> getMyDonations() {
        User user = getCurrentUser();
        return donationRepository.findByRequesterId(user.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }
}