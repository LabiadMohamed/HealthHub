package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.User;
import com.healthhub.healthhub_backend.enums.BloodType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User , Long> {

    Optional <User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findByIsDonorTrueAndBloodType(BloodType bloodType);
}
