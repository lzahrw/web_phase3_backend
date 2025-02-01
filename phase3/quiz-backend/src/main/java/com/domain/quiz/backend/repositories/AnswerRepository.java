package com.domain.quiz.backend.repositories;


import com.domain.quiz.backend.models.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AnswerRepository extends MongoRepository<Answer, String> {
    // Add custom queries if needed
}
