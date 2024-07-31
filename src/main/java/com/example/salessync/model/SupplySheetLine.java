package com.example.salessync.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "lines")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE lines SET is_deleted = true WHERE id = ?")
public class SupplySheetLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String article;
    @Column(nullable = false)
    private String clothType;
    @Column(nullable = false)
    private Integer age;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private Integer series;
    private Integer quantity;
    @Column(nullable = false)
    private BigDecimal price;
    private Integer totalQuantity;
    private BigDecimal totalPrice;
    private Integer supply;
    private String brand;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "lines_sizes",
            joinColumns = @JoinColumn(name = "line_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "size_id", referencedColumnName = "id")
    )
    private List<Size> sizes;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "sheet_id", nullable = false)
    private SupplySheet sheet;
    @Column(nullable = false)
    private boolean isDeleted = false;
}
