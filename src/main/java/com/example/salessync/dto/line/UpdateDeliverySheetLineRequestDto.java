package com.example.salessync.dto.line;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateDeliverySheetLineRequestDto {
    private String deliveryDate;
    @NotNull
    private Integer weight;
    @NotNull
    private BigDecimal priceUsd;
    private BigDecimal additionalPriceUsd;
    private BigDecimal deliveryPriceLocal;
    @NotNull
    private BigDecimal currency;
}
