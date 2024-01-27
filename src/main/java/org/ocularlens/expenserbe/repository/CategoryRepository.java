package org.ocularlens.expenserbe.repository;

import org.ocularlens.expenserbe.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("SELECT c FROM Category c WHERE c.user.id = ?1 OR c.createdByAdmin = true")
    List<Category> findUserAndAdminCreatedCategoriesByUserId(int userId);
}
