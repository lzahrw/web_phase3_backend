
package com.domain.quiz.backend.repositories;

import com.domain.quiz.backend.models.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    // Custom queries can be added here
    List<Question> findByCategoryId(String categoryId);
    List<Question> findByCreatedById(String userId);
}
