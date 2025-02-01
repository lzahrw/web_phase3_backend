package com.domain.quiz.backend.services;


import com.domain.quiz.backend.models.Answer;
import com.domain.quiz.backend.models.Question;
import com.domain.quiz.backend.models.User;
import com.domain.quiz.backend.repositories.AnswerRepository;
import com.domain.quiz.backend.repositories.QuestionRepository;
import com.domain.quiz.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AnswerService {

    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Submits an answer for a given question by a player. If the submitted answer
     * matches the correct answer of the question, the player's score is incremented.
     *
     * @param playerId   the ID of the player submitting the answer
     * @param questionId the ID of the question being answered
     * @param answerText the answer submitted by the player
     * @return a response containing a success message and a flag indicating correctness
     */
    public AnswerSubmissionResponse submitAnswer(String playerId, String questionId, String answerText) {
        // Save the answer record
        Answer answer = new Answer(playerId, questionId, answerText);
        answerRepository.save(answer);

        boolean isCorrect = false;
        Optional<Question> questionOpt = questionRepository.findById(questionId);
        if (questionOpt.isPresent()) {
            Question question = questionOpt.get();
            if (question.getCorrectAnswer() != null &&
                    question.getCorrectAnswer().equalsIgnoreCase(answerText)) {
                isCorrect = true;
                // Update player's score (for example, +1 per correct answer)
                Optional<User> playerOpt = userRepository.findById(playerId);
                if (playerOpt.isPresent()) {
                    User player = playerOpt.get();
                    player.setScore(player.getScore() + 1);
                    userRepository.save(player);
                }
            }
        }

        AnswerSubmissionResponse response = new AnswerSubmissionResponse();
        response.setMessage("Answer submitted successfully");
        response.setCorrect(isCorrect);
        return response;
    }

    /**
     * A simple DTO for wrapping the answer submission response.
     */
    public static class AnswerSubmissionResponse {
        private String message;
        private boolean correct;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public boolean isCorrect() {
            return correct;
        }

        public void setCorrect(boolean correct) {
            this.correct = correct;
        }
    }
}

