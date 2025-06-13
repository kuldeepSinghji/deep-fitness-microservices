package com.deepfitness.deepaiservice.model;
import com.deepfitness.deepaiservice.enums.UserActivityType;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AIRecommendationsTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        AIRecommendations rec = new AIRecommendations();
        rec.setId("id1");
        rec.setActivityId("act1");
        rec.setUserId("user1");
        rec.setActivityType(UserActivityType.RUNNING);
        rec.setRecommendations("Do more cardio");
        rec.setFullAnalysisPerformanceMetrics("Good");
        rec.setNextWorkoutSuggestions(Arrays.asList("Run 5km", "Stretch"));
        rec.setAreasToImprove(List.of("Endurance"));
        rec.setSafetyGuidelines(List.of("Warm up", "Hydrate"));
        LocalDateTime now = LocalDateTime.now();
        rec.setCreateDate(now);

        assertEquals("id1", rec.getId());
        assertEquals("act1", rec.getActivityId());
        assertEquals("user1", rec.getUserId());
        assertEquals(UserActivityType.RUNNING, rec.getActivityType());
        assertEquals("Do more cardio", rec.getRecommendations());
        assertEquals("Good", rec.getFullAnalysisPerformanceMetrics());
        assertEquals(Arrays.asList("Run 5km", "Stretch"), rec.getNextWorkoutSuggestions());
        assertEquals(List.of("Endurance"), rec.getAreasToImprove());
        assertEquals(List.of("Warm up", "Hydrate"), rec.getSafetyGuidelines());
        assertEquals(now, rec.getCreateDate());
    }

    @Test
    void testAllArgsConstructor() {
        LocalDateTime now = LocalDateTime.now();
        List<String> workouts = Arrays.asList("Push-ups", "Sit-ups");
        List<String> areas = List.of("Strength");
        List<String> safety = List.of("Rest", "Proper form");

        AIRecommendations rec = new AIRecommendations(
                "id2",
                "act2",
                "user2",
                UserActivityType.CYCLING,
                "Increase speed",
                "Average",
                workouts,
                areas,
                safety,
                now
        );

        assertEquals("id2", rec.getId());
        assertEquals("act2", rec.getActivityId());
        assertEquals("user2", rec.getUserId());
        assertEquals(UserActivityType.CYCLING, rec.getActivityType());
        assertEquals("Increase speed", rec.getRecommendations());
        assertEquals("Average", rec.getFullAnalysisPerformanceMetrics());
        assertEquals(workouts, rec.getNextWorkoutSuggestions());
        assertEquals(areas, rec.getAreasToImprove());
        assertEquals(safety, rec.getSafetyGuidelines());
        assertEquals(now, rec.getCreateDate());
    }

    @Test
    void testBuilder() {
        LocalDateTime now = LocalDateTime.now();
        AIRecommendations rec = AIRecommendations.builder()
                .id("id3")
                .activityId("act3")
                .userId("user3")
                .activityType(UserActivityType.SWIMMING)
                .recommendations("Improve technique")
                .fullAnalysisPerformanceMetrics("Excellent")
                .nextWorkoutSuggestions(List.of("Swim 1km"))
                .areasToImprove(List.of("Breathing"))
                .safetyGuidelines(List.of("Supervision"))
                .createDate(now)
                .build();

        assertEquals("id3", rec.getId());
        assertEquals("act3", rec.getActivityId());
        assertEquals("user3", rec.getUserId());
        assertEquals(UserActivityType.SWIMMING, rec.getActivityType());
        assertEquals("Improve technique", rec.getRecommendations());
        assertEquals("Excellent", rec.getFullAnalysisPerformanceMetrics());
        assertEquals(List.of("Swim 1km"), rec.getNextWorkoutSuggestions());
        assertEquals(List.of("Breathing"), rec.getAreasToImprove());
        assertEquals(List.of("Supervision"), rec.getSafetyGuidelines());
        assertEquals(now, rec.getCreateDate());
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        AIRecommendations rec1 = AIRecommendations.builder()
                .id("id4")
                .activityId("act4")
                .userId("user4")
                .activityType(UserActivityType.WALKING)
                .recommendations("Walk daily")
                .fullAnalysisPerformanceMetrics("Good")
                .nextWorkoutSuggestions(List.of("Walk 2km"))
                .areasToImprove(List.of("Consistency"))
                .safetyGuidelines(List.of("Proper shoes"))
                .createDate(now)
                .build();

        AIRecommendations rec2 = AIRecommendations.builder()
                .id("id4")
                .activityId("act4")
                .userId("user4")
                .activityType(UserActivityType.WALKING)
                .recommendations("Walk daily")
                .fullAnalysisPerformanceMetrics("Good")
                .nextWorkoutSuggestions(List.of("Walk 2km"))
                .areasToImprove(List.of("Consistency"))
                .safetyGuidelines(List.of("Proper shoes"))
                .createDate(now)
                .build();

        assertEquals(rec1, rec2);
        assertEquals(rec1.hashCode(), rec2.hashCode());
    }

    @Test
    void testToString() {
        AIRecommendations rec = new AIRecommendations();
        String str = rec.toString();
        assertNotNull(str);
        assertTrue(str.contains("AIRecommendations"));
    }
}
