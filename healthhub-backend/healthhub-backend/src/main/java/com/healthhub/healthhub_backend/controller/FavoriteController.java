package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.FavoriteResponse;
import com.healthhub.healthhub_backend.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@CrossOrigin(origins = "*")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<FavoriteResponse> add(@PathVariable Long bookId) {
        return ResponseEntity.ok(favoriteService.addFavorite(bookId));
    }

    @GetMapping
    public ResponseEntity<List<FavoriteResponse>> getMyFavorites() {
        return ResponseEntity.ok(favoriteService.getMyFavorites());
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> remove(@PathVariable Long bookId) {
        favoriteService.removeFavorite(bookId);
        return ResponseEntity.noContent().build();
    }
}