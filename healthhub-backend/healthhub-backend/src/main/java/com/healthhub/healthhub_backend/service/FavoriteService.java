package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.FavoriteResponse;
import com.healthhub.healthhub_backend.entity.Book;
import com.healthhub.healthhub_backend.entity.Favorite;
import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.repository.BookRepository;
import com.healthhub.healthhub_backend.repository.FavoriteRepository;
import com.healthhub.healthhub_backend.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public FavoriteService(FavoriteRepository favoriteRepository,
                           BookRepository bookRepository,
                           UserRepository userRepository) {
        this.favoriteRepository = favoriteRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    private FavoriteResponse toResponse(Favorite favorite) {
        return new FavoriteResponse(
                favorite.getId(),
                favorite.getBook().getId(),
                favorite.getBook().getTitle(),
                favorite.getBook().getCategory().getName(),
                favorite.getSavedAt()
        );
    }

    // Save a book to favorites
    public FavoriteResponse addFavorite(Long bookId) {
        User user = getCurrentUser();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        if (favoriteRepository.findByBookIdAndUserId(user.getId(), bookId).isPresent()) {
            throw new RuntimeException("Book already in favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setBook(book);

        return toResponse(favoriteRepository.save(favorite));
    }

    // Get all favorites for the logged-in user
    public List<FavoriteResponse> getMyFavorites() {
        User user = getCurrentUser();
        return favoriteRepository.findByUserId(user.getId())
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    // Remove a book from favorites
    @Transactional
    public void removeFavorite(Long bookId) {
        User user = getCurrentUser();
        favoriteRepository.deleteByUserIdAndBookId(user.getId(), bookId);
    }
}