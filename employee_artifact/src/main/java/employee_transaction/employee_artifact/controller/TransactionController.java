package employee_transaction.employee_artifact.controller;


import employee_transaction.employee_artifact.dto.TransactionRequest;
import employee_transaction.employee_artifact.dto.TransactionResponse;
import employee_transaction.employee_artifact.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse create(
            @Valid @RequestBody TransactionRequest request) {

        return transactionService.create(request);
    }

    @GetMapping
    public List<TransactionResponse> getAll() {

        return transactionService.getAll();
    }

    @GetMapping("/{id}")
    public TransactionResponse getById(
            @PathVariable Long id) {

        return transactionService.getById(id);
    }

    @PutMapping("/{id}")
    public TransactionResponse update(
            @PathVariable Long id,
            @Valid @RequestBody TransactionRequest request) {

        return transactionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {

        transactionService.delete(id);
    }

    @GetMapping("/pending")
    public List<TransactionResponse> getPending() {

        return transactionService.getPending();
    }
}