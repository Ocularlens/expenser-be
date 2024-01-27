package org.ocularlens.expenserbe.serviceimpl;

import org.ocularlens.expenserbe.models.Category;
import org.ocularlens.expenserbe.models.Transaction;
import org.ocularlens.expenserbe.models.User;
import org.ocularlens.expenserbe.repository.TransactionRepository;
import org.ocularlens.expenserbe.repository.UserRepository;
import org.ocularlens.expenserbe.services.ITransactionService;
import org.springframework.security.core.Authentication;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final CategoryService categoryService;
    public TransactionService(
            TransactionRepository transactionRepository,
            UserRepository userRepository,
            CategoryService categoryService
    ) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
        this.categoryService = categoryService;
    }
    @Override
    public Transaction createTransaction(
            LocalDateTime transactionDate,
            Double amount,
            String notes,
            int categoryId,
            Authentication authentication
    ) {
        Category category = categoryService.findCategory(categoryId);
        User user = userRepository.findByUsername(authentication.getName()).get();
        return transactionRepository.save(new Transaction(
                LocalDateTime.now(),
                amount,
                notes,
                category,
                user
        ));
    }

    @Override
    public Transaction findTransaction(int transactionId) {
        return null;
    }

    @Override
    public List<Transaction> retrieveTransactions() {
        return null;
    }

    @Override
    public void updateTransaction(LocalDateTime transactionDate, Double amount, String notes, int categoryId) {

    }

    @Override
    public void deleteTransaction(int transactionId) {

    }
}
