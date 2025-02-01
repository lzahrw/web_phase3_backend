package com.domain.quiz.backend.services;


import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    /**
     * Fetches all questions created by a specific designer.
     */
    public List<Question> getQuestionsByDesigner(String designerId) {
        return questionRepository.findByDesignerId(designerId);
    }

    /**
     * Creates a new question.
     */
    public Question createQuestion(Question question) {
        question.setCreatedAt(LocalDateTime.now());
        question.setUpdatedAt(LocalDateTime.now());
        return questionRepository.save(question);
    }

    /**
     * Updates an existing question identified by questionId with the provided updates.
     * Throws a RuntimeException if the question is not found.
     */
    public Question updateQuestion(String questionId, Map<String, Object> updates) {
        Optional<Question> optQuestion = questionRepository.findById(questionId);
        if (!optQuestion.isPresent()) {
            throw new RuntimeException("Question not found");
        }
        Question question = optQuestion.get();
        if (updates.containsKey("title")) {
            question.setTitle((String) updates.get("title"));
        }
        if (updates.containsKey("category")) {
            question.setCategory((String) updates.get("category"));
        }
        if (updates.containsKey("options")) {
            question.setOptions((List<String>) updates.get("options"));
        }
        if (updates.containsKey("correctAnswer")) {
            question.setCorrectAnswer((String) updates.get("correctAnswer"));
        }
        if (updates.containsKey("difficulty")) {
            question.setDifficulty((String) updates.get("difficulty"));
        }
        question.setUpdatedAt(LocalDateTime.now());
        return questionRepository.save(question);
    }

    /**
     * Deletes the question identified by questionId.
     */
    public void deleteQuestion(String questionId) {
        questionRepository.deleteById(questionId);
    }

    /**
     * Fetches questions for a player based on optional category or difficulty filters.
     * If no filter is provided, returns all questions.
     */
    public List<Question> getQuestionsForPlayer(String category, String difficulty) {
        if (category != null && !category.isEmpty()) {
            return questionRepository.findByCategory(category);
        } else if (difficulty != null && !difficulty.isEmpty()) {
            return questionRepository.findByDifficulty(difficulty);
        } else {
            return questionRepository.findAll();
        }
    }
}
