package com.domain.quiz.backend.repositories;

import com.domain.quiz.backend.models.Leaderboard;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface LeaderboardRepository extends MongoRepository<Leaderboard, String> {

    // Custom query to get the leaderboard sorted by points
    List<Leaderboard> findAllByOrderByPointsDesc();

    //  more custom queries if needed
}
