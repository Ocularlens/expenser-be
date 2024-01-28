package org.ocularlens.expenserbe.repository;

import org.ocularlens.expenserbe.models.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    @Query(
            value = "SELECT * FROM transaction WHERE user_id = ?1",
            countQuery = "SELECT count(*) FROM transaction WHERE user_id = ?1",
            nativeQuery = true
    )
    Page<Transaction> findUserTransactions(int id, Pageable pageable);

    @Query(
            value = "SELECT t.* FROM transaction t\n" +
                    "INNER JOIN category c ON c.id = t.category_id\n" +
                    "WHERE t.user_id = ?1 AND c.type = ?2",
            countQuery = "SELECT count(t.id) FROM transaction t\n" +
                    "INNER JOIN category c ON c.id = t.category_id\n" +
                    "WHERE t.user_id = ?1 AND c.type = ?2",
            nativeQuery = true
    )
    Page<Transaction> findUserTypedTransaction(int id, String type,Pageable pageable);
}
