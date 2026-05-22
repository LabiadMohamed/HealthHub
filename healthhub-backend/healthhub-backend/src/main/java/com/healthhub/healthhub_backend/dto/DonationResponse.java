package com.healthhub.healthhub_backend.dto;

import com.healthhub.healthhub_backend.enums.BloodType;
import com.healthhub.healthhub_backend.enums.DonationStatus;
import com.healthhub.healthhub_backend.enums.DonationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DonationResponse {

    private Long id;
    private DonationType type;
    private BloodType bloodType;
    private String medicationName;
    private BigDecimal amount;
    private String currency;
    private DonationStatus status;
    private String notes;
    private String requesterName;
    private String donorName;
    private LocalDateTime createdAt;

    public DonationResponse(Long id, DonationType type, BloodType bloodType,
                            String medicationName, BigDecimal amount, String currency,
                            DonationStatus status, String notes, String requesterName,
                            String donorName, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.bloodType = bloodType;
        this.medicationName = medicationName;
        this.amount = amount;
        this.currency = currency;
        this.status = status;
        this.notes = notes;
        this.requesterName = requesterName;
        this.donorName = donorName;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public DonationType getType() { return type; }
    public BloodType getBloodType() { return bloodType; }
    public String getMedicationName() { return medicationName; }
    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public DonationStatus getStatus() { return status; }
    public String getNotes() { return notes; }
    public String getRequesterName() { return requesterName; }
    public String getDonorName() { return donorName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}