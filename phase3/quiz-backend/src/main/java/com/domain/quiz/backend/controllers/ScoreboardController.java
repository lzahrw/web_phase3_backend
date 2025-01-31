package com.domain.quiz.backend.controllers;


import com.domain.quiz.backend.models.ScoreboardEntry;
import com.domain.quiz.backend.services.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;



@RestController
@RequestMapping("/api/scoreboard")
public class ScoreboardController {

    @Autowired
    private ScoreboardService scoreboardService;

    // GET /api/scoreboard
    @GetMapping
    public ResponseEntity<?> getScoreboard() {
        try {
            List<ScoreboardEntry> scoreboard = scoreboardService.getTopScores();
            return ResponseEntity.ok(Map.of("scoreboard", scoreboard));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Failed to load scoreboard."));
        }
    }

    // Additional endpoints if needed
}
