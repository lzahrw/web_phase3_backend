// src/main/java/com/example/quizapp/controller/QuestionController.java
package com.domain.quiz.backend.controllers;

import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Autowired
    private QuestionRepository questionRepository;

    // Fetch questions created by a specific designer
    // GET /api/questions/designer/{designerId}
    @GetMapping("/designer/{designerId}")
    public ResponseEntity<?> getQuestionsByDesigner(@PathVariable String designerId) {
        List<Question> questions = questionRepository.findByDesignerId(designerId);
        return ResponseEntity.ok(questions);
    }

    // Create Question API
    // POST /api/questions/create
    @PostMapping("/create")
    public ResponseEntity<?> createQuestion(@RequestBody Map<String, Object> questionRequest) {
        // Expected keys: title, category, designerId, and optionally options, correctAnswer, difficulty
        String title = (String) questionRequest.get("title");
        String category = (String) questionRequest.get("category");
        String designerId = (String) questionRequest.get("designerId");
        List<String> options = (List<String>) questionRequest.get("options");
        String correctAnswer = (String) questionRequest.get("correctAnswer");
        String difficulty = (String) questionRequest.get("difficulty");

        Question question = new Question(title, category, designerId, options, correctAnswer, difficulty);
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        questionRepository.save(question);

        Map<String, String> response = new HashMap<>();
        response.put("id", question.getId());
        response.put("message", "Question created successfully");
        return ResponseEntity.ok(response);
    }

    // Update Question API
    // PUT /api/questions/{questionId}
    @PutMapping("/{questionId}")
    public ResponseEntity<?> updateQuestion(@PathVariable String questionId,
                                            @RequestBody Map<String, Object> updateRequest) {
        return questionRepository.findById(questionId).map(question -> {
            if (updateRequest.containsKey("title"))
                question.setTitle((String) updateRequest.get("title"));
            if (updateRequest.containsKey("category"))
                question.setCategory((String) updateRequest.get("category"));
            if (updateRequest.containsKey("options"))
                question.setOptions((List<String>) updateRequest.get("options"));
            if (updateRequest.containsKey("correctAnswer"))
                question.setCorrectAnswer((String) updateRequest.get("correctAnswer"));
            if (updateRequest.containsKey("difficulty"))
                question.setDifficulty((String) updateRequest.get("difficulty"));
            question.setUpdatedAt(LocalDateTime.now());
            questionRepository.save(question);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Question updated successfully");
            return ResponseEntity.ok(response);
        }).orElse(ResponseEntity.notFound().build());
    }

    // Delete Question API
    // DELETE /api/questions/{questionId}
    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable String questionId) {
        questionRepository.deleteById(questionId);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Question deleted successfully");
        return ResponseEntity.ok(response);
    }
}
