package com.domain.quiz.backend.services;


import com.domain.quiz.backend.models.*;
import com.domain.quiz.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.*;


@Service
public class PlayerService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ScoreboardRepository scoreboardRepository;

    public User getProfile(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public List<Question> getRandomQuestions(int limit) {
        return questionRepository.findRandomQuestions(limit);
    }

    public List<Question> getQuestionsByCategory(String categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("Category not found");
        }
        return questionRepository.findByCategoryId(categoryId);
    }

    public List<ScoreboardEntry> getScoreboard() {
        return scoreboardRepository.findTop10ByOrderByScoreDesc();
    }

    // Following Users
    public void followUser(String followerId, String userIdToFollow) {
        if (followerId.equals(userIdToFollow)) {
            throw new RuntimeException("You cannot follow yourself.");
        }

        User userToFollow = userRepository.findById(userIdToFollow)
                .orElseThrow(() -> new RuntimeException("User to follow not found."));

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found."));

        if (follower.getFollowing().contains(userIdToFollow)) {
            throw new RuntimeException("Already following this user.");
        }

        follower.getFollowing().add(userIdToFollow);
        userToFollow.getFollowers().add(followerId);

        userRepository.save(follower);
        userRepository.save(userToFollow);
    }

    public void unfollowUser(String followerId, String userIdToUnfollow) {
        if (followerId.equals(userIdToUnfollow)) {
            throw new RuntimeException("You cannot unfollow yourself.");
        }

        User userToUnfollow = userRepository.findById(userIdToUnfollow)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found."));

        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new RuntimeException("Follower not found."));

        if (!follower.getFollowing().contains(userIdToUnfollow)) {
            throw new RuntimeException("You are not following this user.");
        }

        follower.getFollowing().remove(userIdToUnfollow);
        userToUnfollow.getFollowers().remove(followerId);

        userRepository.save(follower);
        userRepository.save(userToUnfollow);
    }

    // Answering Questions and Updating Scoreboard
    public boolean evaluateAnswer(String username, String questionId, String selectedOption) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        boolean isCorrect = question.getCorrectAnswer().equalsIgnoreCase(selectedOption);
        // Optionally, store the answer details or history
        return isCorrect;
    }

    public void updateScore(String username, int points) {
        ScoreboardEntry entry = scoreboardRepository.findByUsername(username);
        if (entry == null) {
            entry = new ScoreboardEntry(username, points);
        } else {
            entry.setScore(entry.getScore() + points);
        }
        scoreboardRepository.save(entry);
    }
}
