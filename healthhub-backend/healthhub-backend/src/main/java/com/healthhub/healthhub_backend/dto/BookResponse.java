package com.healthhub.healthhub_backend.dto;

import java.time.LocalDateTime;

public class BookResponse {

    private Long id;
    private String title;
    private String summary;
    private String pdfUrl;
    private boolean isPublished;
    private LocalDateTime createdAt;
    private String uploadedByName;
    private String categoryName;
    private String categorySlug;

    public BookResponse(Long id, String title, String summary, String pdfUrl,
                        boolean isPublished, LocalDateTime createdAt,
                        String uploadedByName, String categoryName, String categorySlug) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.pdfUrl = pdfUrl;
        this.isPublished = isPublished;
        this.createdAt = createdAt;
        this.uploadedByName = uploadedByName;
        this.categoryName = categoryName;
        this.categorySlug = categorySlug;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getSummary() { return summary; }
    public String getPdfUrl() { return pdfUrl; }
    public boolean isPublished() { return isPublished; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getUploadedByName() { return uploadedByName; }
    public String getCategoryName() { return categoryName; }
    public String getCategorySlug() { return categorySlug; }
}