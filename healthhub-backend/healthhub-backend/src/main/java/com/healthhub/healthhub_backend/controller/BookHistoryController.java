package com.healthhub.healthhub_backend.controller;

import com.healthhub.healthhub_backend.dto.BookHistoryResponse;
import com.healthhub.healthhub_backend.enums.BookAction;
import com.healthhub.healthhub_backend.service.BookHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*")
public class BookHistoryController {

    private final BookHistoryService bookHistoryService;

    public BookHistoryController(BookHistoryService bookHistoryService) {
        this.bookHistoryService = bookHistoryService;
    }

    // Record a VIEW
    @PostMapping("/view/{bookId}")
    public ResponseEntity<BookHistoryResponse> recordView(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookHistoryService.recordAction(bookId, BookAction.VIEW));
    }

    // Record a DOWNLOAD
    @PostMapping("/download/{bookId}")
    public ResponseEntity<BookHistoryResponse> recordDownload(@PathVariable Long bookId) {
        return ResponseEntity.ok(bookHistoryService.recordAction(bookId, BookAction.DOWNLOAD));
    }

    // Get my history
    @GetMapping
    public ResponseEntity<List<BookHistoryResponse>> getMyHistory() {
        return ResponseEntity.ok(bookHistoryService.getMyHistory());
    }
}