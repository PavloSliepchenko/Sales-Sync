package com.example.salessync.dto.size;

import jakarta.validation.constraints.NotBlank;

public record CreateSizeRequestDto(@NotBlank String name) {
}
