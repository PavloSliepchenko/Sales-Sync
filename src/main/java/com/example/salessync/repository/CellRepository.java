package com.example.salessync.repository;

import com.example.salessync.model.Cell;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CellRepository extends JpaRepository<Cell, Long> {
    List<Cell> findAllByUserIdAndCellType(Long userId, Cell.CellType cellType);
}
