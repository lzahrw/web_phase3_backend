package com.domain.quiz.backend.repositories;
import com.domain.quiz.backend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CategoryRepository extends MongoRepository<Category, String> {
    boolean existsByName(String name);
}
