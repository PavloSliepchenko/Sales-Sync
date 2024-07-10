package com.example.salessync.dto.line;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateLineRequestDto {
    @NotBlank
    private String article;
    @NotBlank
    private String clothType;
    @NotNull
    private Integer age;
    @NotBlank
    private String color;
    @NotNull
    private Integer series;
    private BigDecimal price;
    private Integer supply;
    private String brand;
}
