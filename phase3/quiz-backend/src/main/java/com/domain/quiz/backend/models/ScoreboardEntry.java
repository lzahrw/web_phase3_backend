package com.domain.quiz.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "scoreboard")
public class ScoreboardEntry {

    @Id
    private String id;

    private String username;
    private int score;

    // Default constructor
    public ScoreboardEntry() {
    }

    // Constructor with fields
    public ScoreboardEntry(String username, int score) {
        this.username = username;
        this.score = score;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getScore() {
        return score;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setScore(int score) {
        this.score = score;
    }
}

