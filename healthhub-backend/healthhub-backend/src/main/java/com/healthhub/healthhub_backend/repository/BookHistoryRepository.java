package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.BookHistory;
import com.healthhub.healthhub_backend.enums.BookAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookHistoryRepository extends JpaRepository<BookHistory, Long> {

    List<BookHistory> findByUserIdOrderByCreatedAtDesc(Long UserId);

    List<BookHistory> findByUserIdAndAction(Long UserId, BookAction bookAction);

    boolean existsByUserIdAndBookId(Long userId, Long bookId);

}
