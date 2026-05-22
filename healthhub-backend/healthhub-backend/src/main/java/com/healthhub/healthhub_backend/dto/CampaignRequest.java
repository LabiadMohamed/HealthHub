package com.healthhub.healthhub_backend.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CampaignRequest {

    @NotBlank
    private String title;

    private String description;
    private LocalDate startDate;
    private LocalDate endDate;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }
}