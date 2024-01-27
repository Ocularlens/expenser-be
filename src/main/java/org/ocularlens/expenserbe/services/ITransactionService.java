package org.ocularlens.expenserbe.services;

import org.ocularlens.expenserbe.models.Transaction;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;

public interface ITransactionService {
    Transaction createTransaction(LocalDateTime transactionDate, Double amount, String notes, int categoryId, Authentication authentication);
    Transaction findTransaction(int transactionId);
    List<Transaction> retrieveTransactions();
    void updateTransaction(LocalDateTime transactionDate, Double amount, String notes, int categoryId);
    void deleteTransaction(int transactionId);
}
