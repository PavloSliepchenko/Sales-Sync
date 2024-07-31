package com.example.salessync.dto.sheet;

import jakarta.validation.constraints.NotBlank;

public record CreateSupplySheetRequestDto(@NotBlank String name) {
}
