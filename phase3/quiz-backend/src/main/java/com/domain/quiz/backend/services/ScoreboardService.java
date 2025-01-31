package com.domain.quiz.backend.services;


import com.domain.quiz.backend.models.ScoreboardEntry;
import com.domain.quiz.backend.repositories.ScoreboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class ScoreboardService {

    @Autowired
    private ScoreboardRepository scoreboardRepository;

    public List<ScoreboardEntry> getTopScores() {
        return scoreboardRepository.findTop10ByOrderByScoreDesc();
    }

    public void updateScore(String username, int points) {
        ScoreboardEntry entry = scoreboardRepository.findByUsername(username);
        if (entry == null) {
            entry = new ScoreboardEntry(username, points);
        } else {
            entry.setScore(entry.getScore() + points);
        }
        scoreboardRepository.save(entry);
    }
}

