package com.healthhub.healthhub_backend.dto;

import com.healthhub.healthhub_backend.enums.CampaignStatus;
import java.time.LocalDate;

public class CampaignResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate endDate;
    private CampaignStatus status;
    private String createdByName;
    private int volunteerCount;

    public CampaignResponse(Long id, String title, String description,
                            LocalDate startDate, LocalDate endDate,
                            CampaignStatus status, String createdByName,
                            int volunteerCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdByName = createdByName;
        this.volunteerCount = volunteerCount;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public CampaignStatus getStatus() { return status; }
    public String getCreatedByName() { return createdByName; }
    public int getVolunteerCount() { return volunteerCount; }
}