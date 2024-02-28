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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(getClass());

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
    public List<Category> retrieveCategories(String type, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();

        if (!Objects.isNull(type)) {

            logger.info("Transaction type {}", type);

            try {
                TransactionType.valueOf(type);
            } catch (IllegalArgumentException e) {
                throw new NotFoundException("type:" + type);
            }
            return categoryRepository.findCategoriesByUserIdAndTypeAndCreatedByAdmin(
                    user.getId(),
                    TransactionType.valueOf(type)
            );
        }

        logger.info("Transaction type not set");

        return categoryRepository.findCategoriesByUserAndCreatedByAdmin(user.getId());
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

        logger.info("Category with id of {} - updated", id);
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

        logger.info("Category with id of {} - deleted", id);
    }

    @Override
    public Category findCategory(int id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isEmpty()) throw new NotFoundException("categoryId:" + id);

        logger.info("Category with id of {} - found", id);

        return category.get();
    }

    @Override
    public List<Category> retrieveUserCategories(Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();

        return categoryRepository.findCategoriesByUserId(user.getId());
    }
}
