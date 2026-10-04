package employee_transaction.employee_artifact.repository;





import employee_transaction.employee_artifact.entity.Employee;
import employee_transaction.employee_artifact.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    List<Transaction> findByStatus(String status);

    List<Transaction> findByTransactionDate(LocalDate transactionDate);

    List<Transaction> findByEmployeeId(String employeeId);

    long countByTransactionDate(LocalDate transactionDate);

    long countByTransactionDateAndStatus(
            LocalDate transactionDate,
            String status
    );
}