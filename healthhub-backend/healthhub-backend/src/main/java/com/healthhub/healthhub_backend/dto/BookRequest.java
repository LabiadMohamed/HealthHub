package com.healthhub.healthhub_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookRequest {

    @NotBlank
    private String title;

    private String summary;
    private String pdfUrl;

    @NotNull
    private Integer categoryId;

    private boolean isPublished = true;

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }

    public Integer getCategoryId() { return categoryId; }
    public void setCategoryId(Integer categoryId) { this.categoryId = categoryId; }

    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean published) { isPublished = published; }
}