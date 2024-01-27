package org.ocularlens.expenserbe.repository;

import org.ocularlens.expenserbe.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
}
