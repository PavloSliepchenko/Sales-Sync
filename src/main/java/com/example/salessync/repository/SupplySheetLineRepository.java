package com.example.salessync.repository;

import com.example.salessync.model.SupplySheetLine;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplySheetLineRepository extends JpaRepository<SupplySheetLine, Long> {
    Optional<SupplySheetLine> findByArticleAndAgeAndClothTypeAndColorAndBrandAndSheetId(
            String article, String age, String clothType, String color, String brand, Long sheetId
    );
}
