package com.deepfitness.deepaiservice.service;

import com.deepfitness.deepaiservice.model.AIRecommendations;

import java.util.List;

public interface AIRecommendationsService {
    List<AIRecommendations> getUserAIRecommendations(String userId);
    AIRecommendations getUserActivityRecommendations(String activityId);
}
