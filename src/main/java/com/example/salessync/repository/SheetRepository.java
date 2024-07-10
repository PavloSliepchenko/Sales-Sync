package com.example.salessync.repository;

import com.example.salessync.model.Sheet;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheetRepository extends JpaRepository<Sheet, Long> {
    Optional<Sheet> findByIdAndUserId(Long id, Long userId);

    Optional<Sheet> findByName(String name);

    List<Sheet> findAllByUserId(Long userId);
}
