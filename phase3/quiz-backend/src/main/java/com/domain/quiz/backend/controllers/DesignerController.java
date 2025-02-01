package com.domain.quiz.backend.controllers;

import com.domain.quiz.backend.models.Category;
import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.services.DesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/designer")
public class DesignerController {

    @Autowired
    private DesignerService designerService;

    @PostMapping("/categories")
    public Category createCategory(@RequestBody Category category) {
        // You can add user authentication logic here
        String createdById = "userId"; // Get the authenticated user ID
        return designerService.createCategory(category.getName(), createdById);
    }

    @GetMapping("/categories")
    public List<Category> getCategories() {
        return designerService.getCategories();
    }

    @PostMapping("/questions")
    public Question createQuestion(@RequestBody Question question) {
        // You can add user authentication logic here
        String createdById = "userId"; // Get the authenticated user ID
        return designerService.createQuestion(question.getText(), question.getOptions(),
                question.getCorrectAnswer(), question.getDifficulty(),
                question.getCategory().getId(), createdById);
    }

    @PutMapping("/questions/{id}")
    public Question updateQuestion(@PathVariable String id, @RequestBody Question question) {
        return designerService.updateQuestion(id, question.getText(), question.getOptions(),
                question.getCorrectAnswer(), question.getDifficulty(),
                question.getCategory().getId());
    }

    @GetMapping("/questions")
    public List<Question> getQuestions() {
        return designerService.getQuestions();
    }
}
