package com.deepfitness.deepaiservice.controller;
import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.service.AIRecommendationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AIRecommendationsControllerTest {

    private AIRecommendationsService aiRecommendationsService;
    private AIRecommendationsController controller;

    @BeforeEach
    void setUp() {
        aiRecommendationsService = mock(AIRecommendationsService.class);
        controller = new AIRecommendationsController(aiRecommendationsService);
    }

    @Test
    void testGetUserAIRecommendations_ReturnsList() {
        String userId = "user123";
        AIRecommendations rec1 = new AIRecommendations();
        AIRecommendations rec2 = new AIRecommendations();
        List<AIRecommendations> recommendations = Arrays.asList(rec1, rec2);

        when(aiRecommendationsService.getUserAIRecommendations(userId)).thenReturn(recommendations);

        ResponseEntity<List<AIRecommendations>> response = controller.getUserAIRecommendations(userId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(recommendations, response.getBody());
        verify(aiRecommendationsService, times(1)).getUserAIRecommendations(userId);
    }

    @Test
    void testGetUserAIRecommendations_ReturnsEmptyList() {
        String userId = "user456";
        when(aiRecommendationsService.getUserAIRecommendations(userId)).thenReturn(List.of());

        ResponseEntity<List<AIRecommendations>> response = controller.getUserAIRecommendations(userId);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(aiRecommendationsService, times(1)).getUserAIRecommendations(userId);
    }

    @Test
    void testGetUserActivityRecommendations_ReturnsRecommendation() {
        String activityId = "activity789";
        AIRecommendations recommendation = new AIRecommendations();

        when(aiRecommendationsService.getUserActivityRecommendations(activityId)).thenReturn(recommendation);

        ResponseEntity<AIRecommendations> response = controller.getUserActivityRecommendations(activityId);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(recommendation, response.getBody());
        verify(aiRecommendationsService, times(1)).getUserActivityRecommendations(activityId);
    }

    @Test
    void testGetUserActivityRecommendations_ReturnsNull() {
        String activityId = "activity000";
        when(aiRecommendationsService.getUserActivityRecommendations(activityId)).thenReturn(null);

        ResponseEntity<AIRecommendations> response = controller.getUserActivityRecommendations(activityId);

        assertEquals(200, response.getStatusCode().value());
        assertNull(response.getBody());
        verify(aiRecommendationsService, times(1)).getUserActivityRecommendations(activityId);
    }
}
