package org.ocularlens.expenserbe.repository;

import org.ocularlens.expenserbe.common.TransactionType;
import org.ocularlens.expenserbe.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.user.id = ?1 OR c.createdByAdmin = true")
    List<Category> findCategoriesByUserAndCreatedByAdmin(int userId);

    @Query("SELECT c FROM Category c WHERE c.user.id = ?1 OR c.createdByAdmin = true AND c.type = ?2")
    List<Category> findCategoriesByUserIdAndTypeAndCreatedByAdmin(int userId, TransactionType type);
    List<Category> findCategoriesByUserId(int userId);
}
