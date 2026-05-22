package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.BookRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookRatingRepository extends JpaRepository<BookRating, Long> {

    List<BookRating> findByBookId(Long BookId);

    Optional<BookRating> findByBookIdAndUserId(Long BookId, Long UserID);

    boolean existsByBookIdAndUserId(Long BookId, Long UserId);

    @Query("SELECT AVG(r.score) FROM BookRating r WHERE r.book.id = :bookId")
    Double findAverageScoreByBookId(@Param("bookId") Long bookId);
}
