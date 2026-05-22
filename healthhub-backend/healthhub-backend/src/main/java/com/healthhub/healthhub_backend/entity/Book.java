package com.healthhub.healthhub_backend.entity;

import jakarta.persistence.*;
import  lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name ="books")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor


public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String summary;

    @Column(length = 500)
    private String pdfUrl;

    @Column(nullable = false)
    private boolean isPublished;

    @Column(nullable = false)
    private LocalDateTime createdAt= LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "book")
    private List<BookRating> ratings;

    @OneToMany(mappedBy = "book")
    private List<Favorite> favorites;

    @OneToMany(mappedBy = "book")
    private List<BookHistory> history;

}
