package com.domain.quiz.backend.controllers;


import com.domain.quiz.backend.models.Category;
import com.domain.quiz.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;




@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // DTO for creating a new category
    public static class CreateCategoryRequest {
        @NotBlank(message = "Category name cannot be empty")
        private String name;

        public CreateCategoryRequest() {}

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    // POST /api/categories
    @PostMapping
    @PreAuthorize("hasRole('DESIGNER')")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        try {
            Category created = categoryService.createCategory(new Category(request.getName()));
            return ResponseEntity.ok(Map.of("category", created));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // GET /api/categories
    @GetMapping
    public ResponseEntity<?> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(Map.of("categories", categories));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load categories."));
        }
    }
}


