package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {
    List<Article> findByIsPublishedTrue();

    Optional<Article> findBySlug(String slug);

    List<Article> findByTitleContainingIgnoreCaseAndIsPublishedTrue(String Keyword);

    List<Article> findByAuthorId(Long AuthorId);
}
