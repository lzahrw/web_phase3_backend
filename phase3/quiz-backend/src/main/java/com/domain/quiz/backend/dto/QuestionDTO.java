package com.domain.quiz.backend.dto;
import java.util.List;

public class QuestionDTO {
    private String id;
    private String text;
    private List<String> options;
    private String correctAnswer;
    private String difficulty;
    private String categoryId;
    private String categoryName;
    private String designerName;

    public QuestionDTO() {}

    public QuestionDTO(String id, String text, List<String> options, String correctAnswer, String difficulty, String categoryId, String categoryName, String designerName) {
        this.id = id;
        this.text = text;
        this.options = options;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.designerName = designerName;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getDesignerName() {
        return designerName;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setDesignerName(String designerName) {
        this.designerName = designerName;
    }
}
