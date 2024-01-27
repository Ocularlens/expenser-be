package org.ocularlens.expenserbe.services;

import org.ocularlens.expenserbe.models.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICategoryService {
    Category createCategory(String type, Authentication authentication, String categoryName);
    List<Category> retrieveCategories(Authentication authentication);
    void updateCategoryById(int id, String type, Authentication authentication, String categoryName);
    void deleteCategoryById(int id, Authentication authentication);
    Category findCategory(int id);
}
