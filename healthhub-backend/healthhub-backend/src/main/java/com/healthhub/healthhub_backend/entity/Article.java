package com.healthhub.healthhub_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "articles")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Column(nullable = false, unique = true, length = 255)
    private String slug;

    @Column(length = 300)
    private String metaDescription;

    @Column(nullable = false)
    private boolean isPublished = false;

    private LocalDateTime publishedAt;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
}
