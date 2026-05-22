package com.healthhub.healthhub_backend.dto;

import java.time.LocalDateTime;

public class BookRatingResponse {

    private Long id;
    private Long bookId;
    private String userName;
    private int score;
    private String comment;
    private LocalDateTime createdAt;

    public BookRatingResponse(Long id, Long bookId, String userName,
                              int score, String comment, LocalDateTime createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.userName = userName;
        this.score = score;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getUserName() { return userName; }
    public int getScore() { return score; }
    public String getComment() { return comment; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}