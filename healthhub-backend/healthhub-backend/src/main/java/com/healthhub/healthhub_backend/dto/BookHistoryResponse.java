package com.healthhub.healthhub_backend.dto;

import com.healthhub.healthhub_backend.enums.BookAction;
import java.time.LocalDateTime;

public class BookHistoryResponse {

    private Long id;
    private Long bookId;
    private String bookTitle;
    private BookAction action;
    private LocalDateTime createdAt;

    public BookHistoryResponse(Long id, Long bookId, String bookTitle,
                               BookAction action, LocalDateTime createdAt) {
        this.id = id;
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.action = action;
        this.createdAt = createdAt;
    }

    public Long getId() { return id; }
    public Long getBookId() { return bookId; }
    public String getBookTitle() { return bookTitle; }
    public BookAction getAction() { return action; }
    public LocalDateTime getCreatedAt() { return createdAt; }
}