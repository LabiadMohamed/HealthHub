package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.DonationRequest;
import com.healthhub.healthhub_backend.dto.DonationResponse;
import com.healthhub.healthhub_backend.enums.DonationStatus;
import com.healthhub.healthhub_backend.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@CrossOrigin(origins = "*")
public class DonationController {

    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    // Any authenticated user can create a donation request
    @PostMapping
    public ResponseEntity<DonationResponse> create(@Valid @RequestBody DonationRequest request) {
        return ResponseEntity.ok(donationService.createDonation(request));
    }

    // Any authenticated user can respond to a donation
    @PostMapping("/{id}/respond")
    public ResponseEntity<DonationResponse> respond(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.respondToDonation(id));
    }

    // Admin — update donation status
    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DonationResponse> updateStatus(@PathVariable Long id,
                                                         @RequestParam DonationStatus status) {
        return ResponseEntity.ok(donationService.updateStatus(id, status));
    }

    // Public — list open donations
    @GetMapping("/open")
    public ResponseEntity<List<DonationResponse>> getOpen() {
        return ResponseEntity.ok(donationService.getOpenDonations());
    }

    // Authenticated — my donations
    @GetMapping("/my")
    public ResponseEntity<List<DonationResponse>> getMy() {
        return ResponseEntity.ok(donationService.getMyDonations());
    }
}