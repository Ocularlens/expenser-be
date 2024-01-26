package org.ocularlens.expenserbe.controller;

import jakarta.validation.Valid;
import org.ocularlens.expenserbe.models.Category;
import org.ocularlens.expenserbe.repository.CategoryRepository;
import org.ocularlens.expenserbe.requests.AddCategoryRequest;
import org.ocularlens.expenserbe.serviceimpl.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("category")
public class CategoryController {
    private CategoryService categoryService;

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
}
