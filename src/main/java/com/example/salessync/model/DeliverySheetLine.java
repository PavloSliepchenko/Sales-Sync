package com.example.salessync.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "delivery_sheet_lines")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE delivery_sheet_lines SET is_deleted = true WHERE id = ?")
public class DeliverySheetLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String article;
    private String deliveryDate;
    @Column(nullable = false)
    private int weight;
    @Column(nullable = false)
    private BigDecimal priceUsd;
    private BigDecimal additionalPriceUsd;
    private BigDecimal deliveryPriceLocal;
    private Integer supply;
    private BigDecimal deliveryPricePerUnitUsd;
    private BigDecimal totalPriceUsd;
    private BigDecimal deliveryPricePerUnitLocal;
    private BigDecimal averagePriceLocal;
    private BigDecimal currency;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "sheet_id", nullable = false)
    private DeliverySheet sheet;
    @Column(nullable = false)
    private boolean isDeleted = false;
}
