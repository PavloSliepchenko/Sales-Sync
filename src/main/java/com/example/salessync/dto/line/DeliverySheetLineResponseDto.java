package com.example.salessync.dto.line;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class DeliverySheetLineResponseDto {
    private Long id;
    private String article;
    private String deliveryDate;
    private int weight;
    private BigDecimal priceUsd;
    private BigDecimal additionalPriceUsd;
    private BigDecimal deliveryPriceLocal;
    private Integer supply;
    private BigDecimal deliveryPricePerUnitUsd;
    private BigDecimal totalPriceUsd;
    private BigDecimal deliveryPricePerUnitLocal;
    private BigDecimal averagePriceLocal;
    private BigDecimal currency;
}
