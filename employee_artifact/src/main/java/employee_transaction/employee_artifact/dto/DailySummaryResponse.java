package employee_transaction.employee_artifact.dto;


import employee_transaction.employee_artifact.entity.DailySummary;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DailySummaryResponse(
        Long id,
        LocalDate reportDate,
        Long totalTransactions,
        Long validTransactions,
        Long invalidTransactions,
        BigDecimal totalAmount
) {

    public static DailySummaryResponse fromEntity(DailySummary summary) {

        return new DailySummaryResponse(
                summary.getId(),
                summary.getReportDate(),
                summary.getTotalTransactions(),
                summary.getValidTransactions(),
                summary.getInvalidTransactions(),
                summary.getTotalAmount()
        );
    }
}