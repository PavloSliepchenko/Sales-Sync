package com.example.salessync.dto.line;

import com.example.salessync.dto.size.UpdateSizeRequestDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class CreateSupplySheetLineRequestDto {
    @NotBlank
    private String article;
    @NotBlank
    private String clothType;
    @NotBlank
    private String age;
    @NotBlank
    private String color;
    @NotNull
    private Integer series;
    private BigDecimal price;
    private Integer supply;
    @NotNull
    private String brand;
    private List<UpdateSizeRequestDto> sizes;
}
