package com.domain.quiz.backend.repositories;
import com.domain.quiz.backend.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
import com.domain.quiz.backend.models.Role;



import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    List<User> findByRole(Role role);
}
