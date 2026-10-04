package employee_transaction.employee_artifact.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "daily_summary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailySummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "report_date", nullable = false, unique = true)
    private LocalDate reportDate;

    @Column(name = "total_transactions")
    private Long totalTransactions;

    @Column(name = "valid_transactions")
    private Long validTransactions;

    @Column(name = "invalid_transactions")
    private Long invalidTransactions;

    @Column(name = "total_amount", precision = 15, scale = 2)
    private BigDecimal totalAmount;
}