package com.example.salessync.repository;

import com.example.salessync.model.Line;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LineRepository extends JpaRepository<Line, Long> {
}
