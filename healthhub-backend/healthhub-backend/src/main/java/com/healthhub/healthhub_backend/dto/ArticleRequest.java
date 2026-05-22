package com.healthhub.healthhub_backend.dto;

import jakarta.validation.constraints.NotBlank;

public class ArticleRequest {

    @NotBlank
    private String title;

    @NotBlank
    private String body;

    private String metaDescription;
    private boolean isPublished = false;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }

    public String getMetaDescription() { return metaDescription; }
    public void setMetaDescription(String metaDescription) { this.metaDescription = metaDescription; }

    public boolean isPublished() { return isPublished; }
    public void setPublished(boolean published) { isPublished = published; }
}