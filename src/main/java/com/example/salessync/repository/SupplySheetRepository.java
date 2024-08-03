package com.example.salessync.repository;

import com.example.salessync.model.SupplySheet;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplySheetRepository extends JpaRepository<SupplySheet, Long> {
    Optional<SupplySheet> findByIdAndUserId(Long id, Long userId);

    Optional<SupplySheet> findByUserId(Long userId);
}
