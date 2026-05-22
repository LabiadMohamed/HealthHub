package com.healthhub.healthhub_backend.repository;

import com.healthhub.healthhub_backend.entity.Donation;
import com.healthhub.healthhub_backend.enums.DonationStatus;
import com.healthhub.healthhub_backend.enums.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {

    List<Donation> findByStatus(DonationStatus status);

    List<Donation> findByType(DonationType type);

    List<Donation> findByTypeAndStatus(DonationType type, DonationStatus status);

    List<Donation> findByRequesterId(Long requesterId);

    List<Donation> findByDonorId(Long donorId);
}