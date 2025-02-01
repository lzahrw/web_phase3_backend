package com.domain.quiz.backend.controllers;

import com.domain.quiz.backend.services.AnswerService;
import com.domain.quiz.backend.services.AnswerService.AnswerSubmissionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Autowired
    private AnswerService answerService;

    // POST /api/answers/submit
    @PostMapping("/submit")
    public ResponseEntity<?> submitAnswer(@RequestBody SubmitAnswerRequest request) {
        AnswerSubmissionResponse response = answerService.submitAnswer(
                request.getPlayerId(),
                request.getQuestionId(),
                request.getAnswer()
        );
        return ResponseEntity.ok(Map.of(
                "message", response.getMessage(),
                "correct", response.isCorrect()
        ));
    }

    public static class SubmitAnswerRequest {
        private String playerId;
        private String questionId;
        private String answer;

        // Getters and setters
        public String getPlayerId() { return playerId; }
        public void setPlayerId(String playerId) { this.playerId = playerId; }
        public String getQuestionId() { return questionId; }
        public void setQuestionId(String questionId) { this.questionId = questionId; }
        public String getAnswer() { return answer; }
        public void setAnswer(String answer) { this.answer = answer; }
    }
}
