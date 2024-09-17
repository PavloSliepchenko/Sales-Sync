package com.example.salessync.dto.cell;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateCellRequestDto {
    @NotNull
    private String name;
    @NotNull
    private int value;
}
