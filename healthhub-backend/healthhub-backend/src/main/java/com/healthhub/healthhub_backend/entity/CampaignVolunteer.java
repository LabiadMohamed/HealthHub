package com.healthhub.healthhub_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "campaign_volunteers")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class CampaignVolunteer {

    @EmbeddedId
    private CampaignVolunteerId id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();

    @ManyToOne
    @MapsId("campaignId")
    @JoinColumn(name = "campaign_id", nullable = false)
    private Campaign campaign;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}