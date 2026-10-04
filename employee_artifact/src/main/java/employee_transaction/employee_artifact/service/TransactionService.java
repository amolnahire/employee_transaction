package employee_transaction.employee_artifact.service;

import employee_transaction.employee_artifact.dto.TransactionRequest;
import employee_transaction.employee_artifact.dto.TransactionResponse;
import employee_transaction.employee_artifact.entity.Transaction;
import employee_transaction.employee_artifact.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionResponse create(TransactionRequest request) {

        Transaction transaction = Transaction.builder()
                .employeeId(request.employeeId())
                .amount(request.amount())
                .category(request.category())
                .transactionDate(request.transactionDate())
                .description(request.description())
                .status("PENDING")
                .build();

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public List<TransactionResponse> getAll() {

        return transactionRepository.findAll()
                .stream()
                .map(TransactionResponse::fromEntity)
                .toList();
    }

    public TransactionResponse getById(Long id) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transaction not found: " + id
                        )
                );

        return TransactionResponse.fromEntity(transaction);
    }

    public TransactionResponse update(
            Long id,
            TransactionRequest request) {

        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Transaction not found: " + id
                        )
                );

        transaction.setEmployeeId(request.employeeId());
        transaction.setAmount(request.amount());
        transaction.setCategory(request.category());
        transaction.setTransactionDate(request.transactionDate());
        transaction.setDescription(request.description());

        return TransactionResponse.fromEntity(
                transactionRepository.save(transaction)
        );
    }

    public void delete(Long id) {

        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException(
                    "Transaction not found: " + id
            );
        }

        transactionRepository.deleteById(id);
    }

    public List<TransactionResponse> getPending() {

        return transactionRepository
                .findByStatus("PENDING")
                .stream()
                .map(TransactionResponse::fromEntity)
                .toList();
    }
}
