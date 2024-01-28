package org.ocularlens.expenserbe.repository;

import org.ocularlens.expenserbe.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(
            value = "SELECT * FROM transaction WHERE user_id = ?1",
            countName = "SELECT count(*) FROM transaction WHERE user_id = ?1",
            nativeQuery = true
    )
    Page<Transaction> findUserTransactions(int id, Pageable pageable);
}
