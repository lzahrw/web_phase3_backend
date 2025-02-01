
package com.domain.quiz.backend.services;

import com.domain.quiz.backend.models.Category;
import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.repositories.CategoryRepository;
import com.domain.quiz.backend.repositories.QuestionRepository;
import com.domain.quiz.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DesignerService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Question createQuestion(String text, List<String> options, String correctAnswer, String difficulty, String categoryId, String createdById) {
        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        Optional<User> userOpt = userRepository.findById(createdById);

        if (!categoryOpt.isPresent() || !userOpt.isPresent()) {
            return null;
        }

        Category category = categoryOpt.get();
        User createdBy = userOpt.get();

        Question question = new Question(text, options, correctAnswer, difficulty, category, createdBy, null);
        return questionRepository.save(question);
    }

    public List<Question> getQuestions() {
        return questionRepository.findAll();
    }

    public Category createCategory(String name, String createdById) {
        Optional<User> userOpt = userRepository.findById(createdById);
        if (!userOpt.isPresent()) {
            return null;
        }
        User createdBy = userOpt.get();
        Category category = new Category(name, createdBy);
        return categoryRepository.save(category);
    }

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Question updateQuestion(String id, String text, List<String> options, String correctAnswer, String difficulty, String categoryId) {
        Optional<Question> questionOpt = questionRepository.findById(id);
        if (!questionOpt.isPresent()) {
            return null;
        }

        Question question = questionOpt.get();
        question.setText(text);
        question.setOptions(options);
        question.setCorrectAnswer(correctAnswer);
        question.setDifficulty(difficulty);

        Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
        categoryOpt.ifPresent(question::setCategory);

        return questionRepository.save(question);
    }
}
