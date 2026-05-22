package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.CampaignVolunteer;
import com.healthhub.healthhub_backend.entity.CampaignVolunteerId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignVolunteerRepository extends JpaRepository<CampaignVolunteer, CampaignVolunteerId> {

    List<CampaignVolunteer> findByCampaignId(Long campaignId);

    List<CampaignVolunteer> findByUserId(Long userId);

    boolean existsById(CampaignVolunteerId id);
}