package org.ocularlens.expenserbe.serviceimpl;

import org.ocularlens.expenserbe.common.TransactionType;
import org.ocularlens.expenserbe.exception.NotFoundException;
import org.ocularlens.expenserbe.models.Category;
import org.ocularlens.expenserbe.models.Transaction;
import org.ocularlens.expenserbe.models.User;
import org.ocularlens.expenserbe.repository.TransactionRepository;
import org.ocularlens.expenserbe.repository.UserRepository;
import org.ocularlens.expenserbe.services.ITransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
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
    public Transaction findTransaction(int transactionId, Authentication authentication) {
        Optional<Transaction> transaction = transactionRepository.findById(transactionId);
        if (transaction.isEmpty()) throw new NotFoundException("transactionId:" + transactionId);

        User user = userRepository.findByUsername(authentication.getName()).get();

        if (user.getId() != transaction.get().getUser().getId())
            throw new NotFoundException("transactionId:" + transactionId);

        return transaction.get();
    }

    @Override
    public Page<Transaction> retrieveTransactions(String type, Pageable pageable, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();

        //using nativeQuery
        if (!Objects.isNull(type)) {
            try {
                TransactionType.valueOf(type);
            } catch (IllegalArgumentException e) {
                throw new NotFoundException("type:" + type);
            }
            return transactionRepository.findUserTypedTransaction(user.getId(), type, PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    pageable.getSortOr(Sort.by(Sort.Direction.ASC, "transaction_date"))
            ));
        }

        return transactionRepository.findUserTransactions(user.getId(), PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                pageable.getSortOr(Sort.by(Sort.Direction.ASC, "transaction_date"))
        ));
    }

    @Override
    public void updateTransaction(
            int transactionId,
            LocalDateTime transactionDate,
            Double amount,
            String notes,
            int categoryId,
            Authentication authentication
    ) {
        Transaction transaction = findTransaction(transactionId, authentication);
        Category category = categoryService.findCategory(categoryId);

        transaction.setAmount(amount);
        transaction.setCategory(category);
        transaction.setTransactionDate(transactionDate);
        transaction.setNotes(notes);

        transactionRepository.save(transaction);
    }

    @Override
    public void deleteTransaction(int transactionId, Authentication authentication) {
        Transaction transaction = findTransaction(transactionId, authentication);
        transactionRepository.delete(transaction);
    }
}
