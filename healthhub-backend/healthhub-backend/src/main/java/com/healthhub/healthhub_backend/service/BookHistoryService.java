package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.BookHistoryResponse;
import com.healthhub.healthhub_backend.entity.Book;
import com.healthhub.healthhub_backend.entity.BookHistory;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.enums.BookAction;
import com.healthhub.healthhub_backend.repository.BookHistoryRepository;
import com.healthhub.healthhub_backend.repository.BookRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookHistoryService {

    private final BookHistoryRepository bookHistoryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookHistoryService(BookHistoryRepository bookHistoryRepository,
                              BookRepository bookRepository,
                              UserRepository userRepository) {
        this.bookHistoryRepository = bookHistoryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private BookHistoryResponse toResponse(BookHistory history) {
        return new BookHistoryResponse(
                history.getId(),
                history.getBook().getId(),
                history.getBook().getTitle(),
                history.getAction(),
                history.getCreatedAt()
        );
    }

    // Record a view or download
    public BookHistoryResponse recordAction(Long bookId, BookAction action) {
        User user = getCurrentUser();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        BookHistory history = new BookHistory();
        history.setUser(user);
        history.setBook(book);
        history.setAction(action);

        return toResponse(bookHistoryRepository.save(history));
    }

    // Get full history for logged-in user
    public List<BookHistoryResponse> getMyHistory() {
        User user = getCurrentUser();
        return bookHistoryRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }
}