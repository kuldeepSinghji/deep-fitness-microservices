package com.deepfitness.deepaiservice.service.impl;

import com.deepfitness.deepaiservice.model.UserActivity;
import com.deepfitness.deepaiservice.repository.AIRecommendationsRepository;
import com.deepfitness.deepaiservice.service.UserActivityAIService;
import com.deepfitness.deepaiservice.service.UserActivityMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActivityMessageListenerImpl implements UserActivityMessageListener {

    private final UserActivityAIService userActivityAIService;
    private final AIRecommendationsRepository aiRecommendationsRepository;

    @RabbitListener(queues = "userActivity.queue")
    @Override
    public void processUserActivity(UserActivity userActivity) {
        log.info("Received User activity for processing: " + userActivity.getActivityId());
        aiRecommendationsRepository.save(userActivityAIService.generateAIRecommendations(userActivity));
    }
}
