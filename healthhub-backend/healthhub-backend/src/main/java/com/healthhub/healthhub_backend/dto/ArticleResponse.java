package com.healthhub.healthhub_backend.dto;

import java.time.LocalDateTime;

public class ArticleResponse {

    private Long id;
    private String title;
    private String body;
    private String slug;
    private String metaDescription;
    private boolean isPublished;
    private String authorName;
    private LocalDateTime publishedAt;

    public ArticleResponse(Long id, String title, String body, String slug,
                           String metaDescription, boolean isPublished,
                           String authorName, LocalDateTime publishedAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.slug = slug;
        this.metaDescription = metaDescription;
        this.isPublished = isPublished;
        this.authorName = authorName;
        this.publishedAt = publishedAt;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getBody() { return body; }
    public String getSlug() { return slug; }
    public String getMetaDescription() { return metaDescription; }
    public boolean isPublished() { return isPublished; }
    public String getAuthorName() { return authorName; }
    public LocalDateTime getPublishedAt() { return publishedAt; }
}