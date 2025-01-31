package com.domain.quiz.backend.controllers;


import com.domain.quiz.backend.dto.CategoryDTO;
import com.domain.quiz.backend.dto.QuestionDTO;
import com.domain.quiz.backend.services.DesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

import java.util.*;



@RestController
@RequestMapping("/api/designer")
@PreAuthorize("hasRole('DESIGNER')")
public class DesignerController {

    @Autowired
    private DesignerService designerService;

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

    // DTO for creating/updating a new question
    public static class CreateQuestionRequest {
        @NotBlank(message = "Question text cannot be empty")
        private String text;

        @NotEmpty(message = "At least one option is required")
        private List<@NotBlank(message = "Option cannot be empty") String> options;

        @NotBlank(message = "Correct answer cannot be empty")
        private String correctAnswer;

        @NotBlank(message = "Category ID cannot be empty")
        private String categoryId;

        @NotBlank(message = "Difficulty level cannot be empty")
        private String difficulty;

        public CreateQuestionRequest() {}

        // Getters and Setters
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<String> getOptions() {
            return options;
        }

        public void setOptions(List<String> options) {
            this.options = options;
        }

        public String getCorrectAnswer() {
            return correctAnswer;
        }

        public void setCorrectAnswer(String correctAnswer) {
            this.correctAnswer = correctAnswer;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getDifficulty() {
            return difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }
    }

    // POST /api/designer/categories
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory(@Valid @RequestBody CreateCategoryRequest request) {
        try {
            CategoryDTO categoryDTO = designerService.createCategory(request.getName());
            return ResponseEntity.ok(Map.of("category", categoryDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // GET /api/designer/categories
    @GetMapping("/categories")
    public ResponseEntity<?> getAllCategories() {
        try {
            List<CategoryDTO> categories = designerService.getAllCategories();
            return ResponseEntity.ok(Map.of("categories", categories));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load categories."));
        }
    }

    // POST /api/designer/questions
    @PostMapping("/questions")
    public ResponseEntity<?> createQuestion(@Valid @RequestBody CreateQuestionRequest request,
                                            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            QuestionDTO questionDTO = designerService.createQuestion(
                    new QuestionDTO(
                            null, // ID will be set by the database
                            request.getText(),
                            request.getOptions(),
                            request.getCorrectAnswer(),
                            request.getDifficulty(),
                            request.getCategoryId(),
                            null, // categoryName will be set in the service
                            null  // designerName will be set in the service
                    ),
                    userDetails.getUsername()
            );
            return ResponseEntity.ok(Map.of("question", questionDTO));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // GET /api/designer/questions
    @GetMapping("/questions")
    public ResponseEntity<?> getAllQuestions() {
        try {
            List<QuestionDTO> questions = designerService.getAllQuestions();
            return ResponseEntity.ok(Map.of("questions", questions));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load questions."));
        }
    }

    // PUT /api/designer/questions/{questionId}
    @PutMapping("/questions/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable String questionId,
                                            @Valid @RequestBody CreateQuestionRequest request,
                                            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            QuestionDTO updatedQuestion = designerService.updateQuestion(questionId, request, userDetails.getUsername());
            return ResponseEntity.ok(Map.of("question", updatedQuestion));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // DELETE /api/designer/questions/{questionId}
    @DeleteMapping("/questions/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable String questionId,
                                            @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            designerService.deleteQuestion(questionId, userDetails.getUsername());
            return ResponseEntity.ok(Map.of("message", "Question deleted successfully."));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    // Additional endpoints can be added here
}
