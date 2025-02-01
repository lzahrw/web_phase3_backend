
package com.domain.quiz.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Document(collection = "leaderboard") // Marks this as a MongoDB collection
public class Leaderboard {

    @Id // MongoDB primary key
    private String id;

    @DBRef // References the User collection
    private User player;

    private int points; // Default is 0

    // Constructors
    public Leaderboard() {}

    public Leaderboard(User player, int points) {
        this.player = player;
        this.points = points;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getPlayer() {
        return player;
    }

    public void setPlayer(User player) {
        this.player = player;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
