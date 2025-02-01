
package com.domain.quiz.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Document(collection = "questions") // Marks this as a MongoDB document
public class Question {

    @Id  // MongoDB primary key
    private String id;

    private String text; // Question text

    private List<String> options; // Array of options

    private String correctAnswer; // Correct answer

    private String difficulty; // Difficulty level

    @DBRef // Reference to Category
    private Category category;

    @DBRef // Reference to User (Designer)
    private User createdBy;

    @DBRef // References to related questions
    private List<Question> relatedQuestions;

    // Constructors
    public Question() {}

    public Question(String text, List<String> options, String correctAnswer, String difficulty,
                    Category category, User createdBy, List<Question> relatedQuestions) {
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.category = category;
        this.createdBy = createdBy;
        this.relatedQuestions = relatedQuestions;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public List<Question> getRelatedQuestions() {
        return relatedQuestions;
    }

    public void setRelatedQuestions(List<Question> relatedQuestions) {
        this.relatedQuestions = relatedQuestions;
    }
}
