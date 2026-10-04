package employee_transaction.employee_artifact.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionRequest(

        @NotBlank
        String employeeId,

        @NotNull
        @Positive
        BigDecimal amount,

        @NotBlank
        String category,

        @NotNull
        LocalDate transactionDate,

        String description
) {
}