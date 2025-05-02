package com.deepfitness.deepaiservice.repository;

import com.deepfitness.deepaiservice.model.AIRecommendations;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AIRecommendationsRepository extends MongoRepository<AIRecommendations,String> {
    List<AIRecommendations> findByUserId(String userId);

    AIRecommendations findByActivityId(String activityId);
}
