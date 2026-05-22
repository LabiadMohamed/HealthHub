package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long>  {

    List<Favorite> findByUserId(Long UserId);

    Optional<Favorite> findByBookIdAndUserId(Long BookId, Long UserId);

    boolean existsByUserIdAndBookId(Long BookId, Long UserId);

    void deleteByUserIdAndBookId(Long userId, Long bookId);
}
