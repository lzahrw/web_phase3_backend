package com.domain.quiz.backend.repositories;

import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import java.util.List;



public interface QuestionRepository extends MongoRepository<Question, String>, QuestionRepositoryCustom {
    List<Question> findByCategoryId(String categoryId);

    List<Question> findByDesignerUsername(String designerUsername);
}

