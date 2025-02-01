package com.domain.quiz.backend.repositories;
import com.domain.quiz.backend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findByDesignerId(String designerId);
}

