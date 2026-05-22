package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.ArticleRequest;
import com.healthhub.healthhub_backend.dto.ArticleResponse;
import com.healthhub.healthhub_backend.entity.Article;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.repository.ArticleRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public ArticleService(ArticleRepository articleRepository,
                          UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private String toSlug(String title) {
        return title.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-");
    }

    private ArticleResponse toResponse(Article article) {
        return new ArticleResponse(
                article.getId(),
                article.getTitle(),
                article.getBody(),
                article.getSlug(),
                article.getMetaDescription(),
                article.isPublished(),
                article.getAuthor().getName(),
                article.getPublishedAt()
        );
    }

    // Admin — create article
    public ArticleResponse create(ArticleRequest request) {
        User author = getCurrentUser();

        Article article = new Article();
        article.setTitle(request.getTitle());
        article.setBody(request.getBody());
        article.setSlug(toSlug(request.getTitle()));
        article.setMetaDescription(request.getMetaDescription());
        article.setAuthor(author);
        article.setPublished(request.isPublished());

        if (request.isPublished()) {
            article.setPublishedAt(LocalDateTime.now());
        }

        return toResponse(articleRepository.save(article));
    }

    // Admin — update article
    public ArticleResponse update(Long id, ArticleRequest request) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found"));

        article.setTitle(request.getTitle());
        article.setBody(request.getBody());
        article.setSlug(toSlug(request.getTitle()));
        article.setMetaDescription(request.getMetaDescription());
        article.setPublished(request.isPublished());

        if (request.isPublished() && article.getPublishedAt() == null) {
            article.setPublishedAt(LocalDateTime.now());
        }

        return toResponse(articleRepository.save(article));
    }

    // Admin — delete
    public void delete(Long id) {
        if (!articleRepository.existsById(id)) {
            throw new RuntimeException("Article not found");
        }
        articleRepository.deleteById(id);
    }

    // Public — all published
    public List<ArticleResponse> getAllPublished() {
        return articleRepository.findByIsPublishedTrue()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Public — search
    public List<ArticleResponse> search(String keyword) {
        return articleRepository.findByTitleContainingIgnoreCaseAndIsPublishedTrue(keyword)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Public — get by slug
    public ArticleResponse getBySlug(String slug) {
        Article article = articleRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("Article not found"));
        return toResponse(article);
    }
}