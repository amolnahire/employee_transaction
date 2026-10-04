package employee_transaction.employee_artifact.Transaction;

import employee_transaction.employee_artifact.entity.Transaction;
import employee_transaction.employee_artifact.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class TransactionItemProcessor
        implements ItemProcessor<Transaction, Transaction> {

    private final EmployeeRepository employeeRepository;

    @Override
    public Transaction process(Transaction transaction) {

        boolean valid = true;

        // Rule 1: amount must be greater than zero
        if (transaction.getAmount() == null ||
                transaction.getAmount()
                        .compareTo(BigDecimal.ZERO) <= 0) {

            valid = false;
        }

        // Rule 2: employee must exist
        if (!employeeRepository
                .existsByEmployeeId(transaction.getEmployeeId())) {

            valid = false;
        }

        if (valid) {
            transaction.setStatus("VALID");
        } else {
            transaction.setStatus("INVALID");
        }

        transaction.setProcessedAt(LocalDateTime.now());

        return transaction;
    }
}