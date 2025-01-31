package com.domain.quiz.backend.repositories;


import com.domain.quiz.backend.models.ScoreboardEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;


public interface ScoreboardRepository extends MongoRepository<ScoreboardEntry, String> {
    List<ScoreboardEntry> findTop10ByOrderByScoreDesc();
    ScoreboardEntry findByUsername(String username);
}
