package org.ocularlens.expenserbe.services;

import org.ocularlens.expenserbe.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {
    Transaction createTransaction(LocalDateTime transactionDate, Double amount, String notes, int categoryId, Authentication authentication);
    Transaction findTransaction(int transactionId, Authentication authentication);
    Page<Transaction> retrieveTransactions(Pageable pageable, Authentication authentication);
    void updateTransaction(int transactionId, LocalDateTime transactionDate, Double amount, String notes, int categoryId, Authentication authentication);
    void deleteTransaction(int transactionId, Authentication authentication);
}
