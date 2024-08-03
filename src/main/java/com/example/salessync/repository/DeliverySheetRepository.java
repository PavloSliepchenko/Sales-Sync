package com.example.salessync.repository;

import com.example.salessync.model.DeliverySheet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverySheetRepository extends JpaRepository<DeliverySheet, Long> {
    Optional<DeliverySheet> findByUserId(Long userId);
}
