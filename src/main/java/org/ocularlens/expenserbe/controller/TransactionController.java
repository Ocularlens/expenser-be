package org.ocularlens.expenserbe.controller;

import jakarta.validation.Valid;
import org.ocularlens.expenserbe.models.Transaction;
import org.ocularlens.expenserbe.requests.AddTransactionRequest;
import org.ocularlens.expenserbe.requests.UpdateTransactionRequest;
import org.ocularlens.expenserbe.serviceimpl.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transaction")
public class TransactionController {
    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/groupByDate")
    public Map<String, List<Transaction>> getGroupedByDateTransactions(
            @RequestParam int month, Authentication authentication
    ) {
        return transactionService.retrieveTransactions(month, authentication);
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> retrieveTransactions(
            @RequestParam(required = false) String type,
            Authentication authentication,
            Pageable pageable
    ) {
        Page<Transaction> page = transactionService.retrieveTransactions(type, pageable, authentication);
        return ResponseEntity.ok(page.getContent());
    }

    @PostMapping
    public ResponseEntity<Transaction> addTransaction(
            @Valid @RequestBody AddTransactionRequest addTransactionRequest,
            Authentication authentication
    ) {
        Transaction newTransaction = transactionService.createTransaction(
                addTransactionRequest.transactionDate(),
                addTransactionRequest.amount(),
                addTransactionRequest.notes(),
                addTransactionRequest.categoryId(),
                authentication
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTransaction.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{transactionId}")
    public Transaction retrieveTransaction(@PathVariable int transactionId, Authentication authentication) {
        return transactionService.findTransaction(transactionId, authentication);
    }

    @PutMapping("/{transactionId}")
    public void updateTransaction(
            @Valid @RequestBody UpdateTransactionRequest updateTransactionRequest,
            @PathVariable int transactionId,
            Authentication authentication
    ) {
        transactionService.updateTransaction(
                transactionId,
                updateTransactionRequest.transactionDate(),
                updateTransactionRequest.amount(),
                updateTransactionRequest.notes(),
                updateTransactionRequest.categoryId(),
                authentication
        );
    }

    @DeleteMapping("/{transactionId}")
    public void deleteTransaction(@PathVariable int transactionId, Authentication authentication) {
        transactionService.deleteTransaction(transactionId, authentication);
    }
}
