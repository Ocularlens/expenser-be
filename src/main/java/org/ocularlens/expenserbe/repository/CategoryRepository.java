package org.ocularlens.expenserbe.repository;

import org.ocularlens.expenserbe.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
