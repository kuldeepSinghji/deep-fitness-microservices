package com.deepfitness.deepaiservice.service.impl;

import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.repository.AIRecommendationsRepository;
import com.deepfitness.deepaiservice.service.AIRecommendationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AIRecommendationServiceImpl implements AIRecommendationsService {

    private final AIRecommendationsRepository aiRecommendationsRepository;

    public List<AIRecommendations> getUserAIRecommendations(String userId) {
        return aiRecommendationsRepository.findByUserId(userId);
    }

    public AIRecommendations getUserActivityRecommendations(String activityId) {
        return aiRecommendationsRepository.findByActivityId(activityId);
    }
}
