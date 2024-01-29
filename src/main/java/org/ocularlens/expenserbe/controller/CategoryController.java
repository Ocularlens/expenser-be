package org.ocularlens.expenserbe.controller;

import jakarta.validation.Valid;
import org.ocularlens.expenserbe.models.Category;
import org.ocularlens.expenserbe.repository.CategoryRepository;
import org.ocularlens.expenserbe.requests.AddCategoryRequest;
import org.ocularlens.expenserbe.requests.UpdateCategoryRequest;
import org.ocularlens.expenserbe.serviceimpl.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<Category> addCategory(
            @Valid @RequestBody AddCategoryRequest addCategoryRequest,
            Authentication authentication
    ) {
        Category newCategory = categoryService.createCategory(
                addCategoryRequest.type(),
                authentication,
                addCategoryRequest.categoryName()
        );

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newCategory.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{categoryId}")
    public Category findCategory(@PathVariable int categoryId) {
        return categoryService.findCategory(categoryId);
    }

    @GetMapping
    public List<Category> retrieveCategories(@RequestParam(required = false) String type, Authentication authentication) {
        return categoryService.retrieveCategories(type, authentication);
    }

    @PutMapping("/{categoryId}")
    public void updateCategory(
            @PathVariable int categoryId,
            @RequestBody UpdateCategoryRequest updateCategoryRequest,
            Authentication authentication
    ) {
        categoryService.updateCategoryById(
                categoryId,
                updateCategoryRequest.type(),
                authentication,
                updateCategoryRequest.categoryName()
        );
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategory(@PathVariable int categoryId, Authentication authentication) {
        categoryService.deleteCategoryById(categoryId, authentication);
    }
}
