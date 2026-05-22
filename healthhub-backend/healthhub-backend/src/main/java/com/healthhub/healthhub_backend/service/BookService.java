package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.BookRequest;
import com.healthhub.healthhub_backend.dto.BookResponse;
import com.healthhub.healthhub_backend.entity.Book;
import com.healthhub.healthhub_backend.entity.Category;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.repository.BookRepository;
import com.healthhub.healthhub_backend.repository.CategoryRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository,
                       CategoryRepository categoryRepository,
                       UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    // Utility: entity → response DTO
    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getSummary(),
                book.getPdfUrl(),
                book.isPublished(),
                book.getCreatedAt(),
                book.getUploadedBy().getName(),
                book.getCategory().getName(),
                book.getCategory().getSlug()
        );
    }

    // Get the logged-in user from the JWT
    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Public — all published books
    public List<BookResponse> getAllPublished() {
        return bookRepository.findByIsPublishedTrue()
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Public — search by title
    public List<BookResponse> search(String keyword) {
        return bookRepository.findByTitleContainingIgnoreCaseAndIsPublishedTrue(keyword)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Public — filter by category
    public List<BookResponse> getByCategory(Integer categoryId) {
        return bookRepository.findByCategoryId(categoryId)
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Public — single book
    public BookResponse getById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        return toResponse(book);
    }

    // Admin — create book
    public BookResponse createBook(BookRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        User uploader = getCurrentUser();

        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setSummary(request.getSummary());
        book.setPdfUrl(request.getPdfUrl());
        book.setPublished(request.isPublished());
        book.setCategory(category);
        book.setUploadedBy(uploader);

        return toResponse(bookRepository.save(book));
    }

    // Admin — update book
    public BookResponse updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        book.setTitle(request.getTitle());
        book.setSummary(request.getSummary());
        book.setPdfUrl(request.getPdfUrl());
        book.setPublished(request.isPublished());
        book.setCategory(category);

        return toResponse(bookRepository.save(book));
    }

    // Admin — delete book
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new RuntimeException("Book not found");
        }
        bookRepository.deleteById(id);
    }
}