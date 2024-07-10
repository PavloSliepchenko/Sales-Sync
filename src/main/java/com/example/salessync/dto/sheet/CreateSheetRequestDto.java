package com.example.salessync.dto.sheet;

import jakarta.validation.constraints.NotBlank;

public record CreateSheetRequestDto(@NotBlank String name) {
}
