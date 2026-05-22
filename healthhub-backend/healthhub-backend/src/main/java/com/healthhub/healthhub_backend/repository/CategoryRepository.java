package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category , Integer > {

    Optional<Category> findBySlug(String slug);

    boolean existsByName(String slug);
}
