package com.deepfitness.deepaiservice.service.impl;

import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.model.UserActivity;
import com.deepfitness.deepaiservice.service.GeminiService;
import com.deepfitness.deepaiservice.service.UserActivityAIService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserActivityAIServiceImpl implements UserActivityAIService {

    private final GeminiService geminiService;

    @Override
    public AIRecommendations generateAIRecommendations(UserActivity userActivity) {
        String prompt = createPropmtForUserActivity(userActivity);
        String response = geminiService.callGeminiAPI(prompt);
        log.info("GEMINI RESPONSE FOR user activity: " + userActivity.getActivityId() + "\n"
                + "RESPONSE: " + response);
        return parseGeminiResponse(userActivity, response);

    }

    private AIRecommendations parseGeminiResponse(UserActivity userActivity, String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootJsonNode = mapper.readTree(response);

            JsonNode textNode = rootJsonNode.path("candidates")
                    .get(0)
                    .path("content")
                    .path("parts")
                    .get(0)
                    .path("text");

            String jsonContent = textNode.asText()
                    .replaceAll("```json\\n", "")
                    .replaceAll("\\n```", "")
                    .trim();
//            log.info("PARSED RESPONSE FROM GEMINI AI: " + jsonContent);
            JsonNode analysisJson = mapper.readTree(jsonContent);
            JsonNode analysisNode = analysisJson.path("sessionAnalysis");
            StringBuilder fullAnalysis = new StringBuilder();
            StringBuilder fullAnalysisPerformanceMetrics = new StringBuilder();
            addAnalysisSection(fullAnalysis, analysisNode, "summary", "Summary: ");
            addAnalysisSection(fullAnalysis, analysisNode, "efficiencyScore", "EfficiencyScore: ");
            JsonNode performanceMetrics = analysisNode.path("performanceMetrics");
            addAnalysisSection(fullAnalysisPerformanceMetrics, performanceMetrics, "efficiencyScore", "EfficiencyScore: ");
            addAnalysisSection(fullAnalysisPerformanceMetrics, performanceMetrics, "paceInsights", "Insights: ");
            addAnalysisSection(fullAnalysisPerformanceMetrics, performanceMetrics, "calorieInsights", "CalorieInsights: ");
            addAnalysisSection(fullAnalysisPerformanceMetrics, performanceMetrics, "intensityLevel", "IntensityLevel: ");

            List<String> areaToImprove = extractAreasToImprove(analysisJson.path("areasToImprove"));
            List<String> nextWorkoutSuggestions = extractNextWorkoutSuggestions(analysisJson.path("nextWorkoutSuggestions"));
            List<String> safetyGuidelines = extractSafetyGuidelines(analysisJson.path("safetyGuidelines"));
            return AIRecommendations.builder()
                    .activityId(userActivity.getActivityId())
                    .userId(userActivity.getUserId())
                    .activityType(userActivity.getUserActivityType())
                    .recommendations(fullAnalysis.toString().trim())
                    .fullAnalysisPerformanceMetrics(fullAnalysisPerformanceMetrics.toString().trim())
                    .areasToImprove(areaToImprove)
                    .nextWorkoutSuggestions(nextWorkoutSuggestions)
                    .safetyGuidelines(safetyGuidelines)
                    .createDate(LocalDateTime.now())
                    .build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return createDefaultAIRecommendation(userActivity);
        }
    }

    private AIRecommendations createDefaultAIRecommendation(UserActivity userActivity) {
        return
                AIRecommendations.builder()
                        .activityId(userActivity.getActivityId())
                        .userId(userActivity.getUserId())
                        .activityType(userActivity.getUserActivityType())
                        .recommendations("Due to some reason unable to generate recommendations")
                        .fullAnalysisPerformanceMetrics("Due to some reason unable to generate fullAnalysisPerformanceMetrics")
                        .areasToImprove(Collections.singletonList("Please continue with your current routine and exercise"))
                        .nextWorkoutSuggestions(Collections.singletonList("Please consider consulting a fitness professional"))
                        .safetyGuidelines(Arrays.asList(
                                "Always warm up before exercise",
                                "Stay hydrated",
                                "Listen to your body"
                        ))
                        .createDate(LocalDateTime.now())
                        .build();
    }

    private List<String> extractSafetyGuidelines(JsonNode safetyGuidelines) {
        List<String> safety = new ArrayList<>();
        if (safetyGuidelines.isArray()) {
            safetyGuidelines.forEach(item -> safety.add(item.asText()));
        }
        return safety.isEmpty() ? Collections.singletonList("Follow general safety guidelines") : safety;

    }

    private List<String> extractNextWorkoutSuggestions(JsonNode nextWorkoutSuggestions) {
        List<String> suggestion = new ArrayList<>();
        if (nextWorkoutSuggestions.isArray()) {
            nextWorkoutSuggestions.forEach(imp -> {
                String area = imp.path("workoutName").asText();
                String detail = imp.path("details").asText();
                suggestion.add(String.format("%s: %s", area, detail));
            });
        }
        return suggestion.isEmpty() ? Collections.singletonList("No specific next workout Suggestions provided") : suggestion;
    }

    private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
        if (!analysisNode.path(key).isMissingNode()) {
            fullAnalysis.append(prefix)
                    .append(analysisNode.path(key).asText())
                    .append("\n\n");
        }
    }


    private List<String> extractAreasToImprove(JsonNode improveNode) {
        List<String> improve = new ArrayList<>();
        if (improveNode.isArray()) {
            improveNode.forEach(imp -> {
                String area = imp.path("focusArea").asText();
                String detail = imp.path("recommendation").asText();
                improve.add(String.format("%s: %s", area, detail));
            });
        }
        return improve.isEmpty() ? Collections.singletonList("No specific area to improvement provided") : improve;
    }


    private String createPropmtForUserActivity(UserActivity userActivity) {
        String prompt = String.format("""
                        Analyze the following fitness session and provide detailed insights using the EXACT JSON format below:
                            
                        {
                          "sessionAnalysis": {
                            "summary": "Summary of the session",
                            "performanceMetrics": {
                              "paceInsights": "Insights on pace and speed",
                              "heartRateInsights": "Insights on heart rate zones and recovery",
                              "calorieInsights": "CalorieInsights of calories burned",
                              "intensityLevel": "IntensityLevel of workout intensity"
                            },
                            "efficiencyScore": "EfficiencyScore overall score (0-100) estimating workout"
                          },
                          "areasToImprove": [
                            {
                              "focusArea": "Area needing improvement",
                              "recommendation": "Actionable recommendation for improvement"
                            }
                          ],
                          "nextWorkoutSuggestions": [
                            {
                              "workoutName": "Suggested workout",
                              "details": "Detailed description and purpose"
                            }
                          ],
                          "safetyGuidelines": [
                            "First safety tip",
                            "Second safety tip"
                          ]
                        }

                        Fitness Session Details:
                        Activity Type: %s
                        Duration: %d minutes
                        Calories Burned: %d
                        Additional Metrics: %s

                        Please analyze this session focusing on performance, recovery, improvement opportunities, future workout suggestions, and safety. 
                        Response must be strictly in the EXACT JSON format above with clear, insightful, and complete information.
                        """,
                userActivity.getUserActivityType(),
                userActivity.getActivityDuration(),
//                distance,
//                steps,
                userActivity.getCaloriesBurned(),
//                averageHeartRate,
//                maxHeartRate,
//                elevationGain,
//                intensityLevel,
//                notes,
                userActivity.getAdditionalMetrics());
        return prompt;
    }
}
