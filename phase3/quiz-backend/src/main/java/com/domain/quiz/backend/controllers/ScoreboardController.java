package com.domain.quiz.backend.controllers;


import com.domain.quiz.backend.services.ScoreboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;



import com.domain.quiz.backend.models.Role;
import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/scoreboard")
public class ScoreboardController {

    @Autowired
    private UserRepository userRepository;

    // Fetch Scoreboard API (Authorization token required)
    // GET /api/scoreboard
    @GetMapping
    public ResponseEntity<?> getScoreboard() {
        // Get all players and sort them by score descending
        List<User> players = userRepository.findByRole(Role.PLAYER);
        players.sort(Comparator.comparingInt(User::getScore).reversed());

        List<Map<String, Object>> scoreboard = new ArrayList<>();
        int rank = 1;
        for (User player : players) {
            Map<String, Object> entry = new HashMap<>();
            entry.put("playerId", player.getId());
            entry.put("username", player.getUsername());
            entry.put("score", player.getScore());
            entry.put("rank", rank++);
            scoreboard.add(entry);
        }
        return ResponseEntity.ok(scoreboard);
    }
}

