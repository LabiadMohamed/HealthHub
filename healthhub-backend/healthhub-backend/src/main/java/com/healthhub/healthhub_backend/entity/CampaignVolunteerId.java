package com.healthhub.healthhub_backend.entity;

import jakarta.persistence.Embeddable;
import lombok.*;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CampaignVolunteerId implements Serializable {

    private Long campaignId;
    private Long userId;
}