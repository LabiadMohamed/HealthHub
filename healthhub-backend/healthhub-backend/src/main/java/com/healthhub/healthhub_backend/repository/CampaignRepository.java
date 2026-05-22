package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.Campaign;
import com.healthhub.healthhub_backend.enums.CampaignStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CampaignRepository extends JpaRepository<Campaign, Long> {

    List<Campaign> findByStatus(CampaignStatus status);

    List<Campaign> findByCreatedById(Long adminId);
}