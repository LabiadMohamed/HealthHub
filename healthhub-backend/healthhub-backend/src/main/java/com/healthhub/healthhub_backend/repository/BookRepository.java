package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book , Long> {

    List<Book> findByIsPublishedTrue();

    List<Book> findByCategoryId(Integer CategoryId);

    List<Book> findByTitleContainingIgnoreCaseAndIsPublishedTrue(String Keyword);

    List<Book> findByUploadedById(Long UserId);
}
