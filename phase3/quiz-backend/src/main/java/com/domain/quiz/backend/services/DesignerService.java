package com.domain.quiz.backend.services;

import com.domain.quiz.backend.dto.CategoryDTO;
import com.domain.quiz.backend.dto.QuestionDTO;
import com.domain.quiz.backend.models.Category;
import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.repositories.CategoryRepository;
import com.domain.quiz.backend.repositories.QuestionRepository;
import com.domain.quiz.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class DesignerService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new category
    public CategoryDTO createCategory(String categoryName) {
        if (categoryRepository.existsByName(categoryName)) {
            throw new RuntimeException("Category already exists");
        }
        Category category = new Category(categoryName);
        Category savedCategory = categoryRepository.save(category);
        return new CategoryDTO(savedCategory.getId(), savedCategory.getName());
    }

    // Retrieve all categories
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(cat -> new CategoryDTO(cat.getId(), cat.getName()))
                .collect(Collectors.toList());
    }

    // Create a new question
    public QuestionDTO createQuestion(QuestionDTO questionDTO, String designerUsername) {
        // Validate category
        Category category = categoryRepository.findById(questionDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        // Validate designer
        User designer = userRepository.findByUsername(designerUsername)
                .orElseThrow(() -> new RuntimeException("Designer not found"));

        // Create and save question
        Question question = new Question();
        question.setText(questionDTO.getText());
        question.setOptions(questionDTO.getOptions());
        question.setCorrectAnswer(questionDTO.getCorrectAnswer());
        question.setDifficulty(questionDTO.getDifficulty());
        question.setCategory(category);
        question.setDesigner(designer);

        Question savedQuestion = questionRepository.save(question);

        // Map to DTO
        return mapToDTO(savedQuestion);
    }

    // Retrieve all questions
    public List<QuestionDTO> getAllQuestions() {
        List<Question> questions = questionRepository.findAll();

        return questions.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // Update a question
    public QuestionDTO updateQuestion(String questionId, com.domain.quiz.backend.controllers.DesignerController.CreateQuestionRequest request, String designerUsername) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Verify that the current user is the designer of the question
        if (!existingQuestion.getDesigner().getUsername().equals(designerUsername)) {
            throw new RuntimeException("You are not authorized to update this question.");
        }

        // Update fields
        existingQuestion.setText(request.getText());
        existingQuestion.setOptions(request.getOptions());
        existingQuestion.setCorrectAnswer(request.getCorrectAnswer());
        existingQuestion.setDifficulty(request.getDifficulty());

        if (!existingQuestion.getCategory().getId().equals(request.getCategoryId())) {
            Category newCategory = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            existingQuestion.setCategory(newCategory);
        }

        // Save updated question
        Question updatedQuestion = questionRepository.save(existingQuestion);

        // Map to DTO
        return mapToDTO(updatedQuestion);
    }

    // Delete a question
    public void deleteQuestion(String questionId, String designerUsername) {
        Question existingQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        // Verify that the current user is the designer of the question
        if (!existingQuestion.getDesigner().getUsername().equals(designerUsername)) {
            throw new RuntimeException("You are not authorized to delete this question.");
        }

        questionRepository.delete(existingQuestion);
    }

    // Helper method to map Question to QuestionDTO
    private QuestionDTO mapToDTO(Question question) {
        return new QuestionDTO(
                question.getId(),
                question.getText(),
                question.getOptions(),
                question.getCorrectAnswer(),
                question.getDifficulty(),
                question.getCategory().getId(),
                question.getCategory().getName(),
                question.getDesigner().getUsername()
        );
    }
}

