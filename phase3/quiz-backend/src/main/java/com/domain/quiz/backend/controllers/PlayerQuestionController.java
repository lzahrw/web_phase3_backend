package com.domain.quiz.backend.controllers;


import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class PlayerQuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // Fetch Questions API for player with optional filters
    // GET /api/questions/player?category=...&difficulty=...
    @GetMapping("/player")
    public ResponseEntity<?> getQuestionsForPlayer(@RequestParam(required = false) String category,
                                                   @RequestParam(required = false) String difficulty) {
        List<Question> questions;
        if (category != null) {
            questions = questionRepository.findByCategory(category);
        } else if (difficulty != null) {
            questions = questionRepository.findByDifficulty(difficulty);
        } else {
            questions = questionRepository.findAll();
        }
        return ResponseEntity.ok(questions);
    }
}
