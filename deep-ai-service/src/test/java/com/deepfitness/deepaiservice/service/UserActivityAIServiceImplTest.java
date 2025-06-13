package com.deepfitness.deepaiservice.service;

import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.model.UserActivity;
import com.deepfitness.deepaiservice.enums.UserActivityType;
import com.deepfitness.deepaiservice.service.impl.UserActivityAIServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class UserActivityAIServiceImplTest {

    private GeminiService geminiService;
    private UserActivityAIServiceImpl userActivityAIService;

    @BeforeEach
    void setUp() {
        geminiService = mock(GeminiService.class);
        userActivityAIService = new UserActivityAIServiceImpl(geminiService);
    }

    private UserActivity buildUserActivity() {
        UserActivity activity = new UserActivity();
        activity.setActivityId("a1");
        activity.setUserId("u1");
        activity.setUserActivityType(UserActivityType.RUNNING);
        activity.setActivityDuration(30);
        activity.setCaloriesBurned(250);
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("distance", 5.0);
        activity.setAdditionalMetrics(metrics);
        return activity;
    }

    @Test
    void testGenerateAIRecommendations_ParsesGeminiResponse() {
        UserActivity activity = buildUserActivity();

        // Gemini's response is a JSON with a "candidates" array, etc.
        String geminiResponse = """
        {
          "candidates": [
            {
              "content": {
                "parts": [
                  {
                    "text": "```json\\n{\\n  \\"sessionAnalysis\\": {\\n    \\"summary\\": \\"Great run!\\",\\n    \\"performanceMetrics\\": {\\n      \\"paceInsights\\": \\"Good pace\\",\\n      \\"calorieInsights\\": \\"Burned 250 calories\\",\\n      \\"intensityLevel\\": \\"Moderate\\"\\n    },\\n    \\"efficiencyScore\\": \\"85\\"\\n  },\\n  \\"areasToImprove\\": [\\n    {\\n      \\"focusArea\\": \\"Endurance\\",\\n      \\"recommendation\\": \\"Increase distance gradually\\"\\n    }\\n  ],\\n  \\"nextWorkoutSuggestions\\": [\\n    {\\n      \\"workoutName\\": \\"Interval Training\\",\\n      \\"details\\": \\"Alternate fast and slow running\\"\\n    }\\n  ],\\n  \\"safetyGuidelines\\": [\\n    \\"Warm up\\",\\n    \\"Hydrate\\"\\n  ]\\n}\\n```"
                  }
                ]
              }
            }
          ]
        }
        """;

        when(geminiService.callGeminiAPI(anyString())).thenReturn(geminiResponse);

        AIRecommendations rec = userActivityAIService.generateAIRecommendations(activity);

        assertNotNull(rec);
        assertEquals("a1", rec.getActivityId());
        assertEquals("u1", rec.getUserId());
        assertEquals(UserActivityType.RUNNING, rec.getActivityType());
        assertTrue(rec.getRecommendations().contains("Summary: Great run!"));
        assertTrue(rec.getFullAnalysisPerformanceMetrics().contains("CalorieInsights: Burned 250 calories"));
        assertTrue(rec.getAreasToImprove().get(0).contains("Endurance"));
        assertTrue(rec.getNextWorkoutSuggestions().get(0).contains("Interval Training"));
        assertTrue(rec.getSafetyGuidelines().contains("Warm up"));
        assertTrue(rec.getSafetyGuidelines().contains("Hydrate"));

        // Verify prompt was generated and sent to GeminiService
        ArgumentCaptor<String> promptCaptor = ArgumentCaptor.forClass(String.class);
        verify(geminiService, times(1)).callGeminiAPI(promptCaptor.capture());
        String prompt = promptCaptor.getValue();
        assertTrue(prompt.contains("Activity Type: RUNNING"));
        assertTrue(prompt.contains("Calories Burned: 250"));
    }

    @Test
    void testGenerateAIRecommendations_ReturnsDefaultOnParseError() {
        UserActivity activity = buildUserActivity();
        // Malformed response triggers catch block
        when(geminiService.callGeminiAPI(anyString())).thenReturn("not a valid json");

        AIRecommendations rec = userActivityAIService.generateAIRecommendations(activity);

        assertNotNull(rec);
        assertEquals("a1", rec.getActivityId());
        assertEquals("u1", rec.getUserId());
        assertEquals("Due to some reason unable to generate recommendations", rec.getRecommendations());
        assertEquals("Due to some reason unable to generate fullAnalysisPerformanceMetrics", rec.getFullAnalysisPerformanceMetrics());
        assertEquals(List.of("Please continue with your current routine and exercise"), rec.getAreasToImprove());
        assertEquals(List.of("Please consider consulting a fitness professional"), rec.getNextWorkoutSuggestions());
        assertTrue(rec.getSafetyGuidelines().contains("Always warm up before exercise"));
    }

    @Test
    void testGenerateAIRecommendations_EmptySections() {
        UserActivity activity = buildUserActivity();

        // Response with empty arrays for areasToImprove, nextWorkoutSuggestions, safetyGuidelines
        String geminiResponse = """
        {
          "candidates": [
            {
              "content": {
                "parts": [
                  {
                    "text": "```json\\n{\\n  \\"sessionAnalysis\\": {\\n    \\"summary\\": \\"Nice!\\",\\n    \\"performanceMetrics\\": {},\\n    \\"efficiencyScore\\": \\"80\\"\\n  },\\n  \\"areasToImprove\\": [],\\n  \\"nextWorkoutSuggestions\\": [],\\n  \\"safetyGuidelines\\": []\\n}\\n```"
                  }
                ]
              }
            }
          ]
        }
        """;

        when(geminiService.callGeminiAPI(anyString())).thenReturn(geminiResponse);

        AIRecommendations rec = userActivityAIService.generateAIRecommendations(activity);

        assertNotNull(rec);
        assertEquals(List.of("No specific area to improvement provided"), rec.getAreasToImprove());
        assertEquals(List.of("No specific next workout Suggestions provided"), rec.getNextWorkoutSuggestions());
        assertEquals(List.of("Follow general safety guidelines"), rec.getSafetyGuidelines());
    }
}
