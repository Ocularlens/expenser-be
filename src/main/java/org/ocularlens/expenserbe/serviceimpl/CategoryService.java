package org.ocularlens.expenserbe.serviceimpl;

import org.ocularlens.expenserbe.common.Role;
import org.ocularlens.expenserbe.common.TransactionType;
import org.ocularlens.expenserbe.custom.util.UserRole;
import org.ocularlens.expenserbe.exception.NotFoundException;
import org.ocularlens.expenserbe.models.Category;
import org.ocularlens.expenserbe.models.User;
import org.ocularlens.expenserbe.repository.CategoryRepository;
import org.ocularlens.expenserbe.repository.UserRepository;
import org.ocularlens.expenserbe.services.ICategoryService;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    private CategoryRepository categoryRepository;
    private UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Category createCategory(String type, Authentication authentication, String categoryName) {
        Role role = UserRole.getScope(authentication);
        boolean isAdmin = role == Role.ADMIN;
        User user = userRepository.findByUsername(authentication.getName()).get();
        return categoryRepository.save(new Category(TransactionType.valueOf(type), categoryName, isAdmin, user));
    }

    @Override
    public List<Category> retrieveCategories(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        return categoryRepository.findUserAndAdminCreatedCategoriesByUserId(user.getId());
    }

    @Override
    public void updateCategoryById(int id, String type, Authentication authentication, String categoryName) {
        Category category = findCategory(id);
        Role role = UserRole.getScope(authentication);
        boolean isAdmin = role == Role.ADMIN;

        User user = userRepository.findByUsername(authentication.getName()).get();
        boolean isCreatedByUser = user.getId() == category.getUser().getId();

        if (!isAdmin || !isCreatedByUser) return;

        category.setType(TransactionType.valueOf(type));
        category.setName(categoryName);
        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(int id, Authentication authentication) {
        Category category = findCategory(id);
        Role role = UserRole.getScope(authentication);
        boolean isAdmin = role == Role.ADMIN;

        User user = userRepository.findByUsername(authentication.getName()).get();
        boolean isCreatedByUser = user.getId() == category.getUser().getId();

        if (!isAdmin || !isCreatedByUser) return;

        categoryRepository.delete(category);
    }

    @Override
    public Category findCategory(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) throw new NotFoundException("categoryId:" + id);
        return category.get();
    }
}
