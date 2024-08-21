package com.example.salessync.dto.size;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSizeRequestDto {
    @NotNull
    private String name;
    @NotNull
    private int number;
}
