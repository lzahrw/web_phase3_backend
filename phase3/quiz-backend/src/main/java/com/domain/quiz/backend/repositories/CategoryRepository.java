
package com.domain.quiz.backend.repositories;

import com.domain.quiz.backend.models.Category;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    // Custom queries can be added here if necessary (e.g., findByName)
    Category findByName(String name);
}
