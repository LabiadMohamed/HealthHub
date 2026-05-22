package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.CampaignRequest;
import com.healthhub.healthhub_backend.dto.CampaignResponse;
import com.healthhub.healthhub_backend.enums.CampaignStatus;
import com.healthhub.healthhub_backend.service.CampaignService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campaigns")
@CrossOrigin(origins = "*")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    // Public
    @GetMapping("/active")
    public ResponseEntity<List<CampaignResponse>> getActive() {
        return ResponseEntity.ok(campaignService.getActive());
    }

    // Admin
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CampaignResponse> create(@Valid @RequestBody CampaignRequest request) {
        return ResponseEntity.ok(campaignService.create(request));
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CampaignResponse> updateStatus(@PathVariable Long id,
                                                         @RequestParam CampaignStatus status) {
        return ResponseEntity.ok(campaignService.updateStatus(id, status));
    }

    // Authenticated users
    @PostMapping("/{id}/join")
    public ResponseEntity<Void> join(@PathVariable Long id) {
        campaignService.joinCampaign(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/leave")
    public ResponseEntity<Void> leave(@PathVariable Long id) {
        campaignService.leaveCampaign(id);
        return ResponseEntity.noContent().build();
    }
}