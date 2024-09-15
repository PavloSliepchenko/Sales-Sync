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
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table(name = "additional_expenses_sheet_lines")
@Where(clause = "is_deleted = false")
@SQLDelete(sql = "UPDATE additional_expenses_sheet_lines SET is_deleted = true WHERE id = ?")
public class AdditionalExpensesSheetLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "lines_cells",
            joinColumns = @JoinColumn(name = "line_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "cell_id", referencedColumnName = "id")
    )
    private List<AdditionalExpensesSheetCell> cells;
    @ManyToOne
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "sheet_id", nullable = false)
    private AdditionalExpensesSheet sheet;
    @Column(nullable = false)
    private boolean isDeleted = false;
}
