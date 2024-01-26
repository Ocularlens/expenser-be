package org.ocularlens.expenserbe.serviceimpl;

import org.ocularlens.expenserbe.common.Role;
import org.ocularlens.expenserbe.common.TransactionType;
import org.ocularlens.expenserbe.custom.util.UserRole;
import org.ocularlens.expenserbe.models.Category;
import org.ocularlens.expenserbe.repository.CategoryRepository;
import org.ocularlens.expenserbe.services.ICategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService implements ICategoryService {

    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category createCategory(String type, Authentication authentication, String categoryName) {
        boolean typeCondition = Objects.equals(type, TransactionType.CREDIT.toString());
        TransactionType transactionType = typeCondition ? TransactionType.CREDIT : TransactionType.DEBIT;
        Role role = UserRole.getScope(authentication);
        boolean isAdmin = role == Role.ADMIN;
        return categoryRepository.save(new Category(transactionType, categoryName, isAdmin));
    }

    @Override
    public List<Category> retrieveCategory() {
        return null;
    }

    @Override
    public void updateCategory(String type, Authentication authentication, String categoryName) {

    }

    @Override
    public void deleteCategory(Category category) {

    }

    @Override
    public Category findCategory(int id) {
        return null;
    }
}
