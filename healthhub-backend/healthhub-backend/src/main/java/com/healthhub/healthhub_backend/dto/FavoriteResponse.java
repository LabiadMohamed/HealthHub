package com.healthhub.healthhub_backend.dto;

import java.time.LocalDateTime;

public class FavoriteResponse {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private String categoryName;
    private LocalDateTime savedAt;

    public FavoriteResponse(Long id, Long bookId, String bookTitle,
                            String categoryName, LocalDateTime savedAt) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.categoryName = categoryName;
        this.savedAt = savedAt;
    }

    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public String getCategoryName() { return categoryName; }
    public LocalDateTime getSavedAt() { return savedAt; }
}