
package com.domain.quiz.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Document(collection = "users") // Marks this as a MongoDB document
public class User {

    @Id // MongoDB primary key
    private String id;

    private String username;
    private String password;

    private String role; // "designer" or "player"

    @DBRef // Users following this user
    private List<User> followers;

    @DBRef // Users this user is following
    private List<User> following;

    private int score; // Player score for leaderboard

    // Constructors
    public User() {}

    public User(String username, String password, String role, List<User> followers, List<User> following, int score) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.followers = followers;
        this.following = following;
        this.score = score;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
