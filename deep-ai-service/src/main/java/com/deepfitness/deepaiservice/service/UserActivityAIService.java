package com.deepfitness.deepaiservice.service;

import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.model.UserActivity;

public interface UserActivityAIService {
    AIRecommendations generateAIRecommendations(UserActivity userActivity);
}
