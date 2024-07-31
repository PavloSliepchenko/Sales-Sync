package com.example.salessync.dto.line;

import com.example.salessync.dto.size.SizeResponseDto;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;

@Data
public class SupplySheetLineResponseDto {
    private Long id;
    private String article;
    private String clothType;
    private Integer age;
    private String color;
    private Integer series;
    private Integer quantity;
    private BigDecimal price;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Integer supply;
    private String brand;
    private List<SizeResponseDto> sizes;
}
