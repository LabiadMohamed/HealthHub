package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.BookRatingRequest;
import com.healthhub.healthhub_backend.dto.BookRatingResponse;
import com.healthhub.healthhub_backend.entity.Book;
import com.healthhub.healthhub_backend.entity.BookRating;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.repository.BookRatingRepository;
import com.healthhub.healthhub_backend.repository.BookRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookRatingService {

    private final BookRatingRepository bookRatingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public BookRatingService(BookRatingRepository bookRatingRepository,
                             BookRepository bookRepository,
                             UserRepository userRepository) {
        this.bookRatingRepository = bookRatingRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private BookRatingResponse toResponse(BookRating rating) {
        return new BookRatingResponse(
                rating.getId(),
                rating.getBook().getId(),
                rating.getUser().getName(),
                rating.getScore(),
                rating.getComment(),
                rating.getCreatedAt()
        );
    }

    // Rate a book — one rating per user per book
    public BookRatingResponse rateBook(BookRatingRequest request) {
        User user = getCurrentUser();

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Check if user already rated this book
        if (bookRatingRepository.findByBookIdAndUserId(book.getId(), user.getId()).isPresent()) {
            throw new RuntimeException("You already rated this book");
        }

        BookRating rating = new BookRating();
        rating.setBook(book);
        rating.setUser(user);
        rating.setScore(request.getScore());
        rating.setComment(request.getComment());

        return toResponse(bookRatingRepository.save(rating));
    }

    // Get all ratings for a book
    public List<BookRatingResponse> getRatingsByBook(Long bookId) {
        return bookRatingRepository.findByBookId(bookId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Get average score for a book
    public Double getAverageScore(Long bookId) {
        return bookRatingRepository.findAverageScoreByBookId(bookId);
    }
}