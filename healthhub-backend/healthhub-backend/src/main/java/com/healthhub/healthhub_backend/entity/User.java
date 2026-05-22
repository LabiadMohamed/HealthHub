package com.healthhub.healthhub_backend.entity;

import com.healthhub.healthhub_backend.enums.BloodType;
import com.healthhub.healthhub_backend.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import com.healthhub.healthhub_backend.entity.Book;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length= 100)
    private String name;

    @Column(nullable = false, length = 150)
    private  String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodType bloodType;

    @Column(name = "is_donor", nullable = false)
    private boolean isDonor = false;

    @Column(name = "is_volunteer", nullable = false )
    private boolean isVolunteer = false;

    @Column(name = "email_subscribed", nullable = false)
    private boolean emailSubscribed = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @OneToMany(mappedBy = "uploadedBy")
    private List<Book> books;

    @OneToMany(mappedBy = "author")
    private List<Article> articles;

    @OneToMany(mappedBy = "createdBy")
    private List<Campaign> campaigns;

    @OneToMany(mappedBy = "requester")
    private List<Donation> donationsRequested;

    @OneToMany(mappedBy = "donor")
    private List<Donation> donationsFulfilled;

    @OneToMany(mappedBy = "user")
    private List<BookRating> ratings;

    @OneToMany(mappedBy = "user")
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "user")
    private List<BookHistory> history;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private EmailSubscription emailSubscription;

    @OneToMany(mappedBy = "user")
    private List<CampaignVolunteer> campaignVolunteers;

}
