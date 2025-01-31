package com.domain.quiz.backend.controllers;

import com.domain.quiz.backend.models.*;
import com.domain.quiz.backend.services.PlayerService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import jakarta.validation.Valid;



@RestController
@RequestMapping("/api/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    // DTO for answer submission
    public static class AnswerDTO {
        @NotBlank(message = "Question ID cannot be empty")
        private String questionId;

        @NotBlank(message = "Selected option cannot be empty")
        private String selectedOption;

        // Getters and Setters
        public String getQuestionId() {
            return questionId;
        }

        public void setQuestionId(String questionId) {
            this.questionId = questionId;
        }

        public String getSelectedOption() {
            return selectedOption;
        }

        public void setSelectedOption(String selectedOption) {
            this.selectedOption = selectedOption;
        }
    }

    // GET /api/player/profile
    @GetMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            User user = playerService.getProfile(userDetails.getUsername());
            return ResponseEntity.ok(Map.of("user", user));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }
    }

    // GET /api/player/categories
    @GetMapping("/categories")
    public ResponseEntity<?> getCategories() {
        try {
            List<Category> categories = playerService.getAllCategories();
            return ResponseEntity.ok(Map.of("categories", categories));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load categories."));
        }
    }

    // GET /api/player/questions/random
    @GetMapping("/questions/random")
    public ResponseEntity<?> getRandomQuestions(@RequestParam(defaultValue = "10") int limit) {
        try {
            List<Question> questions = playerService.getRandomQuestions(limit);
            return ResponseEntity.ok(Map.of("questions", questions));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load random questions."));
        }
    }

    // GET /api/player/questions/category/{categoryId}
    @GetMapping("/questions/category/{categoryId}")
    public ResponseEntity<?> getQuestionsByCategory(@PathVariable String categoryId) {
        try {
            List<Question> questions = playerService.getQuestionsByCategory(categoryId);
            return ResponseEntity.ok(Map.of("questions", questions));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load questions for the selected category."));
        }
    }

    // GET /api/player/scoreboard
    @GetMapping("/scoreboard")
    public ResponseEntity<?> getScoreboard() {
        try {
            List<ScoreboardEntry> scoreboard = playerService.getScoreboard();
            return ResponseEntity.ok(Map.of("scoreboard", scoreboard));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load scoreboard."));
        }
    }

    // POST /api/player/follow/{userId}
    @PostMapping("/follow/{userId}")
    public ResponseEntity<?> followUser(@PathVariable String userId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            User follower = playerService.getProfile(userDetails.getUsername());
            playerService.followUser(follower.getId(), userId);
            return ResponseEntity.ok(Map.of("message", "Successfully followed the user."));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // DELETE /api/player/unfollow/{userId}
    @DeleteMapping("/unfollow/{userId}")
    public ResponseEntity<?> unfollowUser(@PathVariable String userId, @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            User follower = playerService.getProfile(userDetails.getUsername());
            playerService.unfollowUser(follower.getId(), userId);
            return ResponseEntity.ok(Map.of("message", "Successfully unfollowed the user."));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    // POST /api/player/answer
    @PostMapping("/answer")
    public ResponseEntity<?> submitAnswer(@Valid @RequestBody AnswerDTO answerDTO, @AuthenticationPrincipal org.springframework.security.core.userdetails.User userDetails) {
        try {
            boolean isCorrect = playerService.evaluateAnswer(userDetails.getUsername(), answerDTO.getQuestionId(), answerDTO.getSelectedOption());
            if (isCorrect) {
                playerService.updateScore(userDetails.getUsername(), 10); // Example: +10 points for correct answer
                return ResponseEntity.ok(Map.of("message", "Correct answer!", "isCorrect", true));
            } else {
                return ResponseEntity.ok(Map.of("message", "Incorrect answer.", "isCorrect", false));
            }
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }
}

