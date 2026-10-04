package employee_transaction.employee_artifact.dto;


import employee_transaction.employee_artifact.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record TransactionResponse(
        Long id,
        String employeeId,
        BigDecimal amount,
        String category,
        LocalDate transactionDate,
        String description,
        String status,
        LocalDateTime createdAt,
        LocalDateTime processedAt
) {

    public static TransactionResponse fromEntity(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getEmployeeId(),
                transaction.getAmount(),
                transaction.getCategory(),
                transaction.getTransactionDate(),
                transaction.getDescription(),
                transaction.getStatus(),
                transaction.getCreatedAt(),
                transaction.getProcessedAt()
        );
    }
}
