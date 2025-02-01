
package com.domain.quiz.backend.repositories;

import com.domain.quiz.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    // Custom query to find a User by username
    Optional<User> findByUsername(String username);


}
