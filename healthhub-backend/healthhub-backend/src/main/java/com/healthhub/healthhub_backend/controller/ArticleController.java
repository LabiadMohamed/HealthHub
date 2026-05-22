package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.ArticleRequest;
import com.healthhub.healthhub_backend.dto.ArticleResponse;
import com.healthhub.healthhub_backend.service.ArticleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@CrossOrigin(origins = "*")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // Public
    @GetMapping("/published")
    public ResponseEntity<List<ArticleResponse>> getAllPublished() {
        return ResponseEntity.ok(articleService.getAllPublished());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ArticleResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(articleService.search(keyword));
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ArticleResponse> getBySlug(@PathVariable String slug) {
        return ResponseEntity.ok(articleService.getBySlug(slug));
    }

    // Admin
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArticleResponse> create(@Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(articleService.create(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArticleResponse> update(@PathVariable Long id,
                                                  @Valid @RequestBody ArticleRequest request) {
        return ResponseEntity.ok(articleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}