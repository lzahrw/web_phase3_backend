package com.domain.quiz.backend.models;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    // The designer who created this category
    private String designerId;

    public Category() {
    }

    public Category(String name, String description, String designerId) {
        this.name = name;
        this.description = description;
        this.designerId = designerId;
    }

    // Getters and setters ...
}


