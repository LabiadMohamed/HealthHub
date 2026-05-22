package com.healthhub.healthhub_backend.entity;

import com.healthhub.healthhub_backend.enums.BloodType;
import com.healthhub.healthhub_backend.enums.DonationStatus;
import com.healthhub.healthhub_backend.enums.DonationType;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "donations")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Donation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type", columnDefinition = "ENUM('A_POS','A_NEG','B_POS','B_NEG','AB_POS','AB_NEG','O_POS','O_NEG')")
    private BloodType bloodType;

    @Column(length = 200)
    private String medicationName;

    @Column(precision = 10, scale = 2)
    private BigDecimal amount;

    @Column(length = 10)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DonationStatus status = DonationStatus.OPEN;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "requester_id", nullable = false)
    private User requester;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private User donor;
}