package org.ocularlens.expenserbe.services;

import org.ocularlens.expenserbe.models.Category;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ICategoryService {
    Category createCategory(String type, Authentication authentication, String categoryName);
    List<Category> retrieveCategory();
    void updateCategory(String type, Authentication authentication, String categoryName);
    void deleteCategory(Category category);
    Category findCategory(int id);
}
