package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.BookRatingRequest;
import com.healthhub.healthhub_backend.dto.BookRatingResponse;
import com.healthhub.healthhub_backend.service.BookRatingService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@CrossOrigin(origins = "*")
public class BookRatingController {

    private final BookRatingService bookRatingService;

    public BookRatingController(BookRatingService bookRatingService) {
        this.bookRatingService = bookRatingService;
    }

    // Authenticated users can rate
    @PostMapping
    public ResponseEntity<BookRatingResponse> rate(@Valid @RequestBody BookRatingRequest request) {
        return ResponseEntity.ok(bookRatingService.rateBook(request));
    }

    // Public — anyone can see ratings
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookRatingResponse>> getByBook(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookRatingService.getRatingsByBook(bookId));
    }

    // Public — average score
    @GetMapping("/book/{bookId}/average")
    public ResponseEntity<Double> getAverage(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookRatingService.getAverageScore(bookId));
    }
}