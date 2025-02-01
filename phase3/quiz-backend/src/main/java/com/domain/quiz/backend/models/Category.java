//package com.domain.quiz.backend.models;
//
//public class Category {
//}
package com.domain.quiz.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Document(collection = "categories")  // Specifies this as a MongoDB document
public class Category {

    @Id  // Marks this as the primary key
    private String id;

    private String name;

    @DBRef  // References another document (User)
    private User createdBy;

    // Constructors
    public Category() {}

    public Category(String name, User createdBy) {
        this.name = name;
        this.createdBy = createdBy;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
