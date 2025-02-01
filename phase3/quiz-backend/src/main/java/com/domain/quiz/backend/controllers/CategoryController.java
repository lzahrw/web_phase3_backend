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



// src/main/java/com/domain/quiz/backend/controllers/CategoryController.java

import com.domain.quiz.backend.models.Category;
import com.domain.quiz.backend.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.domain.quiz.backend.models.Category;
import com.domain.quiz.backend.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    // Fetch Categories API for a given designer
    // GET /api/categories/designer/{designerId}
    @GetMapping("/designer/{designerId}")
    public ResponseEntity<?> getCategoriesByDesigner(@PathVariable String designerId) {
        List<Category> categories = categoryRepository.findByDesignerId(designerId);
        return ResponseEntity.ok(categories);
    }

    // Optionally, you can add an endpoint to create a new category:
    // POST /api/categories/create
    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        categoryRepository.save(category);
        return ResponseEntity.ok(category);
    }
}

