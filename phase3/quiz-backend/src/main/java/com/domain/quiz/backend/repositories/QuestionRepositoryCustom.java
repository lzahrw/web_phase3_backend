package com.domain.quiz.backend.repositories;

import com.domain.quiz.backend.models.Question;
import java.util.List;

public interface QuestionRepositoryCustom {
    List<Question> findRandomQuestions(int limit);
}
