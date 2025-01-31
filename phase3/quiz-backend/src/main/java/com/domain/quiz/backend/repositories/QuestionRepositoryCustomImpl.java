package com.domain.quiz.backend.repositories;


import com.domain.quiz.backend.models.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class QuestionRepositoryCustomImpl implements QuestionRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Question> findRandomQuestions(int limit) {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.sample(limit)
        );
        AggregationResults<Question> results = mongoTemplate.aggregate(agg, "questions", Question.class);
        return results.getMappedResults();
    }
}
