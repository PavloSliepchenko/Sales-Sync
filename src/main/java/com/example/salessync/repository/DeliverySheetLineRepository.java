package com.example.salessync.repository;

import com.example.salessync.model.DeliverySheetLine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliverySheetLineRepository extends JpaRepository<DeliverySheetLine, Long> {
}
