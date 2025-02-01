package com.domain.quiz.backend.services;


import com.domain.quiz.backend.config.JwtTokenProvider;
import com.domain.quiz.backend.models.Role;
import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    /**
     * Validates credentials and returns a map containing the JWT token,
     * user role and userId. Returns null (or throws an exception) if
     * credentials are invalid.
     */
    public Map<String, String> login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            // IMPORTANT: In production, use hashed passwords and proper authentication!
            if (user.getPassword().equals(password)) {
                String token = jwtTokenProvider.generateToken(user.getId(), user.getUsername(), user.getRole().name());
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("userRole", user.getRole().name().toLowerCase());
                response.put("userId", user.getId());
                return response;
            }
        }
        return null; // Alternatively, you can throw an exception for invalid credentials.
    }

    /**
     * Registers a new designer. Throws a RuntimeException if the username already exists.
     */
    public User registerDesigner(User designer) {
        if (userRepository.findByUsername(designer.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        designer.setRole(Role.DESIGNER);
        designer.setScore(0);
        return userRepository.save(designer);
    }

    /**
     * Registers a new player. Throws a RuntimeException if the username already exists.
     */
    public User registerPlayer(User player) {
        if (userRepository.findByUsername(player.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        player.setRole(Role.PLAYER);
        player.setScore(0);
        return userRepository.save(player);
    }
}
