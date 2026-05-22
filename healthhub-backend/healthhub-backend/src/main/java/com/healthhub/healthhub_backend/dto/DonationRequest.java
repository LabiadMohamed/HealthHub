package com.healthhub.healthhub_backend.dto;

import com.healthhub.healthhub_backend.enums.DonationType;
import com.healthhub.healthhub_backend.enums.BloodType;
import jakarta.validation.constraints.NotNull;

public class DonationRequest {

    @NotNull
    private DonationType type;

    private BloodType bloodType;
    private String medicationName;
    private java.math.BigDecimal amount;
    private String currency;
    private String notes;

    public DonationType getType() { return type; }
    public void setType(DonationType type) { this.type = type; }

    public BloodType getBloodType() { return bloodType; }
    public void setBloodType(BloodType bloodType) { this.bloodType = bloodType; }

    public String getMedicationName() { return medicationName; }
    public void setMedicationName(String medicationName) { this.medicationName = medicationName; }

    public java.math.BigDecimal getAmount() { return amount; }
    public void setAmount(java.math.BigDecimal amount) { this.amount = amount; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}