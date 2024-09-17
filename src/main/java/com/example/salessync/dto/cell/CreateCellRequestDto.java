package com.example.salessync.dto.cell;

import jakarta.validation.constraints.NotBlank;

public record CreateCellRequestDto(@NotBlank String name) {
}
