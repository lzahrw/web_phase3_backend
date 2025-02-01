package com.domain.quiz.backend.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@Configuration
@EnableMongoRepositories(basePackages = "com.domain.quiz.backend.repositories")
public class MongoConfig {
    // Custom MongoDB configurations can be added here if needed
}

