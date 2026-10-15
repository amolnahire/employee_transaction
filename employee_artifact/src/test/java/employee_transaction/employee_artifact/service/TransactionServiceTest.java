package employee_transaction.employee_artifact.service;

import employee_transaction.employee_artifact.dto.TransactionRequest;
import employee_transaction.employee_artifact.dto.TransactionResponse;
import employee_transaction.employee_artifact.entity.Transaction;
import employee_transaction.employee_artifact.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    @Test
    void createSavesPendingTransactionAndReturnsResponse() {
        TransactionRequest request = request();
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(invocation -> {
            Transaction transaction = invocation.getArgument(0);
            transaction.setId(1L);
            return transaction;
        });

        TransactionResponse response = transactionService.create(request);

        ArgumentCaptor<Transaction> captor = ArgumentCaptor.forClass(Transaction.class);
        verify(transactionRepository).save(captor.capture());
        assertEquals("PENDING", captor.getValue().getStatus());
        assertEquals("employee-1", response.employeeId());
        assertEquals(new BigDecimal("125.50"), response.amount());
        assertEquals(1L, response.id());
    }

    @Test
    void getAllReturnsMappedTransactions() {
        Transaction transaction = transaction(1L, "PENDING");
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<TransactionResponse> responses = transactionService.getAll();

        assertEquals(1, responses.size());
        assertEquals(1L, responses.get(0).id());
        assertEquals("PENDING", responses.get(0).status());
    }

    @Test
    void getByIdThrowsWhenTransactionDoesNotExist() {
        when(transactionRepository.findById(42L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.getById(42L)
        );

        assertEquals("Transaction not found: 42", exception.getMessage());
    }

    @Test
    void updateChangesRequestFieldsAndPreservesStatus() {
        Transaction existing = transaction(1L, "APPROVED");
        TransactionRequest request = new TransactionRequest(
                "employee-2",
                new BigDecimal("200.00"),
                "Travel",
                LocalDate.of(2026, 2, 3),
                "Updated details"
        );
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(transactionRepository.save(existing)).thenReturn(existing);

        TransactionResponse response = transactionService.update(1L, request);

        assertEquals("employee-2", response.employeeId());
        assertEquals(new BigDecimal("200.00"), response.amount());
        assertEquals("Travel", response.category());
        assertEquals(LocalDate.of(2026, 2, 3), response.transactionDate());
        assertEquals("Updated details", response.description());
        assertEquals("APPROVED", response.status());
    }

    @Test
    void deleteRemovesExistingTransaction() {
        when(transactionRepository.existsById(1L)).thenReturn(true);

        transactionService.delete(1L);

        verify(transactionRepository).deleteById(1L);
    }

    @Test
    void deleteThrowsWhenTransactionDoesNotExist() {
        when(transactionRepository.existsById(42L)).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> transactionService.delete(42L)
        );

        assertEquals("Transaction not found: 42", exception.getMessage());
        verify(transactionRepository, never()).deleteById(anyLong());
    }

    @Test
    void getPendingReturnsPendingTransactions() {
        when(transactionRepository.findByStatus("PENDING"))
                .thenReturn(List.of(transaction(1L, "PENDING")));

        List<TransactionResponse> responses = transactionService.getPending();

        assertEquals(1, responses.size());
        assertEquals("PENDING", responses.get(0).status());
        verify(transactionRepository).findByStatus("PENDING");
    }

    private static TransactionRequest request() {
        return new TransactionRequest(
                "employee-1",
                new BigDecimal("125.50"),
                "Meals",
                LocalDate.of(2026, 1, 15),
                "Team lunch"
        );
    }

    private static Transaction transaction(Long id, String status) {
        return Transaction.builder()
                .id(id)
                .employeeId("employee-1")
                .amount(new BigDecimal("125.50"))
                .category("Meals")
                .transactionDate(LocalDate.of(2026, 1, 15))
                .description("Team lunch")
                .status(status)
                .build();
    }
}
