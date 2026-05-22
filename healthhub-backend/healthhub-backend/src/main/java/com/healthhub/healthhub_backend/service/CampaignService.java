package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.CampaignRequest;
import com.healthhub.healthhub_backend.dto.CampaignResponse;
import com.healthhub.healthhub_backend.entity.Campaign;
import com.healthhub.healthhub_backend.entity.CampaignVolunteer;
import com.healthhub.healthhub_backend.entity.CampaignVolunteerId;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.enums.CampaignStatus;
import com.healthhub.healthhub_backend.repository.CampaignRepository;
import com.healthhub.healthhub_backend.repository.CampaignVolunteerRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CampaignService {

    private final CampaignRepository campaignRepository;
    private final CampaignVolunteerRepository campaignVolunteerRepository;
    private final UserRepository userRepository;

    public CampaignService(CampaignRepository campaignRepository,
                           CampaignVolunteerRepository campaignVolunteerRepository,
                           UserRepository userRepository) {
        this.campaignRepository = campaignRepository;
        this.campaignVolunteerRepository = campaignVolunteerRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private CampaignResponse toResponse(Campaign campaign) {
        int volunteerCount = campaignVolunteerRepository.findByCampaignId(campaign.getId()).size();
        return new CampaignResponse(
                campaign.getId(),
                campaign.getTitle(),
                campaign.getDescription(),
                campaign.getStartDate(),
                campaign.getEndDate(),
                campaign.getStatus(),
                campaign.getCreatedBy().getName(),
                volunteerCount
        );
    }

    // Admin — create campaign
    public CampaignResponse create(CampaignRequest request) {
        User admin = getCurrentUser();

        Campaign campaign = new Campaign();
        campaign.setTitle(request.getTitle());
        campaign.setDescription(request.getDescription());
        campaign.setStartDate(request.getStartDate());
        campaign.setEndDate(request.getEndDate());
        campaign.setStatus(CampaignStatus.ACTIVE);
        campaign.setCreatedBy(admin);

        return toResponse(campaignRepository.save(campaign));
    }

    // Public — get all active campaigns
    public List<CampaignResponse> getActive() {
        return campaignRepository.findByStatus(CampaignStatus.ACTIVE)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Admin — update status
    public CampaignResponse updateStatus(Long id, CampaignStatus status) {
        Campaign campaign = campaignRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));
        campaign.setStatus(status);
        return toResponse(campaignRepository.save(campaign));
    }

    // User — join campaign
    public void joinCampaign(Long campaignId) {
        User user = getCurrentUser();

        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new RuntimeException("Campaign not found"));

        CampaignVolunteerId id = new CampaignVolunteerId(campaignId, user.getId());

        if (campaignVolunteerRepository.existsById(id)) {
            throw new RuntimeException("Already joined this campaign");
        }

        CampaignVolunteer cv = new CampaignVolunteer();
        cv.setId(id);
        cv.setCampaign(campaign);
        cv.setUser(user);

        campaignVolunteerRepository.save(cv);
    }

    // User — leave campaign
    public void leaveCampaign(Long campaignId) {
        User user = getCurrentUser();
        CampaignVolunteerId id = new CampaignVolunteerId(campaignId, user.getId());
        campaignVolunteerRepository.deleteById(id);
    }
}