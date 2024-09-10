package com.example.salessync.repository;

import com.example.salessync.model.Size;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SizeRepository extends JpaRepository<Size, Long> {
    List<Size> findAllByUserId(Long userId);
}
