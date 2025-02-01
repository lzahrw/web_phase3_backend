package com.domain.quiz.backend.services;


import com.domain.quiz.backend.models.Role;
import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class ScoreboardService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Returns a sorted list of scoreboard entries containing the player's ID,
     * username, score, and rank.
     */
    public List<ScoreboardEntry> getScoreboard() {
        List<User> players = userRepository.findByRole(Role.PLAYER);
        players.sort(Comparator.comparingInt(User::getScore).reversed());

        List<ScoreboardEntry> scoreboard = new ArrayList<>();
        int rank = 1;
        for (User player : players) {
            ScoreboardEntry entry = new ScoreboardEntry();
            entry.setPlayerId(player.getId());
            entry.setUsername(player.getUsername());
            entry.setScore(player.getScore());
            entry.setRank(rank++);
            scoreboard.add(entry);
        }
        return scoreboard;
    }

    /**
     * A simple DTO for representing a scoreboard entry.
     */
    public static class ScoreboardEntry {
        private String playerId;
        private String username;
        private int score;
        private int rank;

        public String getPlayerId() {
            return playerId;
        }

        public void setPlayerId(String playerId) {
            this.playerId = playerId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }
    }
}
