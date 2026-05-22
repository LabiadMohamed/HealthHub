package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.BookRequest;
import com.healthhub.healthhub_backend.dto.BookResponse;
import com.healthhub.healthhub_backend.service.BookService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Public endpoints
    @GetMapping("/published")
    public ResponseEntity<List<BookResponse>> getAllPublished() {
        return ResponseEntity.ok(bookService.getAllPublished());
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponse>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.search(keyword));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<BookResponse>> getByCategory(@PathVariable Integer categoryId) {
        return ResponseEntity.ok(bookService.getByCategory(categoryId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getById(id));
    }

    // Admin endpoints
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> create(@Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.createBook(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponse> update(@PathVariable Long id,
                                               @Valid @RequestBody BookRequest request) {
        return ResponseEntity.ok(bookService.updateBook(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}