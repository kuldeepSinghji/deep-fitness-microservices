package com.deepfitness.deepaiservice.controller;

import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.service.AIRecommendationsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ai/recommendations")
public class AIRecommendationsController {

    private final AIRecommendationsService aiRecommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AIRecommendations>> getUserAIRecommendations(@PathVariable String userId){
        return ResponseEntity.ok(aiRecommendationService.getUserAIRecommendations(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<AIRecommendations> getUserActivityRecommendations(@PathVariable String activityId){
        return ResponseEntity.ok(aiRecommendationService.getUserActivityRecommendations(activityId));
    }
}
