package com.domain.quiz.backend.services;


import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public List<Question> getRandomQuestions(int limit) {
        return questionRepository.findRandomQuestions(PageRequest.of(0, limit));
    }

    public List<Question> getQuestionsByCategory(String categoryId) {
        // Implement as needed, possibly similar to PlayerService
        return null;
    }

    // Additional methods for creating, updating questions
}
