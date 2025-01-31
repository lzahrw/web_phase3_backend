package com.domain.quiz.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "followers")
public class Follower {
    @Id
    private String id;

    private String userId; // The user being followed
    private String followerId; // The follower

    // Constructors, Getters, and Setters
    public Follower() {}

    // Getters and Setters
    // ...
}
