package com.deepfitness.deepaiservice.service;

import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.model.UserActivity;
import com.deepfitness.deepaiservice.repository.AIRecommendationsRepository;
import com.deepfitness.deepaiservice.service.impl.UserActivityMessageListenerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class UserActivityMessageListenerImplTest {

    private UserActivityAIService userActivityAIService;
    private AIRecommendationsRepository aiRecommendationsRepository;
    private UserActivityMessageListenerImpl listener;

    @BeforeEach
    void setUp() {
        userActivityAIService = mock(UserActivityAIService.class);
        aiRecommendationsRepository = mock(AIRecommendationsRepository.class);
        listener = new UserActivityMessageListenerImpl(userActivityAIService, aiRecommendationsRepository);
    }

    @Test
    void testProcessUserActivity_CallsAIServiceAndSavesRecommendation() {
        UserActivity userActivity = new UserActivity();
        userActivity.setActivityId("a1");
        AIRecommendations recommendations = new AIRecommendations();

        when(userActivityAIService.generateAIRecommendations(userActivity)).thenReturn(recommendations);

        listener.processUserActivity(userActivity);

        verify(userActivityAIService, times(1)).generateAIRecommendations(userActivity);
        verify(aiRecommendationsRepository, times(1)).save(recommendations);
    }

    @Test
    void testProcessUserActivity_NullRecommendation() {
        UserActivity userActivity = new UserActivity();
        userActivity.setActivityId("a2");

        when(userActivityAIService.generateAIRecommendations(userActivity)).thenReturn(null);

        listener.processUserActivity(userActivity);

        verify(userActivityAIService, times(1)).generateAIRecommendations(userActivity);
        verify(aiRecommendationsRepository, times(1)).save(null);
    }
}