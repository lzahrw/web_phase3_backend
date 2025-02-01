package com.domain.quiz.backend.controllers;


import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
// src/main/java/com/domain/quiz/backend/controllers/AuthController.java


import java.util.Map;


import com.domain.quiz.backend.config.JwtTokenProvider;
import com.domain.quiz.backend.models.Role;
import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider tokenProvider;

    // Login API: /api/auth/login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // In production use hashed passwords!
            if (user.getPassword().equals(password)) {
                String token = tokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole().name());
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("userRole", user.getRole().name().toLowerCase());
                response.put("userId", user.getId());
                return ResponseEntity.ok(response);
            }
        }
        return ResponseEntity.status(401).body("Invalid credentials");
    }

    // Designer Registration: /api/auth/register/designer
    @PostMapping("/register/designer")
    public ResponseEntity<?> registerDesigner(@RequestBody Map<String, Object> registrationRequest) {
        String username = (String) registrationRequest.get("username");
        String password = (String) registrationRequest.get("password");
        String email = (String) registrationRequest.get("email");
        Map<String, Object> details = (Map<String, Object>) registrationRequest.get("details");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User designer = new User(username, password, email, Role.DESIGNER, details);
        userRepository.save(designer);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Designer registered successfully");
        response.put("userId", designer.getId());
        return ResponseEntity.ok(response);
    }

    // Player Registration: /api/auth/register/player
    @PostMapping("/register/player")
    public ResponseEntity<?> registerPlayer(@RequestBody Map<String, Object> registrationRequest) {
        String username = (String) registrationRequest.get("username");
        String password = (String) registrationRequest.get("password");
        String email = (String) registrationRequest.get("email");
        Map<String, Object> details = (Map<String, Object>) registrationRequest.get("details");

        if (userRepository.findByUsername(username).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        User player = new User(username, password, email, Role.PLAYER, details);
        userRepository.save(player);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Player registered successfully");
        response.put("userId", player.getId());
        return ResponseEntity.ok(response);
    }
}

