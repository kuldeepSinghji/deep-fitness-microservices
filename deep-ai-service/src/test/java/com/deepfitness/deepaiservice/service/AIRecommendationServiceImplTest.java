package com.deepfitness.deepaiservice.service;
import com.deepfitness.deepaiservice.model.AIRecommendations;
import com.deepfitness.deepaiservice.repository.AIRecommendationsRepository;
import com.deepfitness.deepaiservice.service.impl.AIRecommendationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AIRecommendationServiceImplTest {

    private AIRecommendationsRepository repository;
    private AIRecommendationServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(AIRecommendationsRepository.class);
        service = new AIRecommendationServiceImpl(repository);
    }

    @Test
    void testGetUserAIRecommendations_ReturnsList() {
        String userId = "user1";
        AIRecommendations rec1 = new AIRecommendations();
        AIRecommendations rec2 = new AIRecommendations();
        List<AIRecommendations> expected = Arrays.asList(rec1, rec2);

        when(repository.findByUserId(userId)).thenReturn(expected);

        List<AIRecommendations> result = service.getUserAIRecommendations(userId);

        assertEquals(expected, result);
        verify(repository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetUserAIRecommendations_ReturnsEmptyList() {
        String userId = "nouser";
        when(repository.findByUserId(userId)).thenReturn(List.of());

        List<AIRecommendations> result = service.getUserAIRecommendations(userId);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByUserId(userId);
    }

    @Test
    void testGetUserActivityRecommendations_ReturnsRecommendation() {
        String activityId = "activity1";
        AIRecommendations rec = new AIRecommendations();

        when(repository.findByActivityId(activityId)).thenReturn(rec);

        AIRecommendations result = service.getUserActivityRecommendations(activityId);

        assertEquals(rec, result);
        verify(repository, times(1)).findByActivityId(activityId);
    }

    @Test
    void testGetUserActivityRecommendations_ReturnsNull() {
        String activityId = "noactivity";
        when(repository.findByActivityId(activityId)).thenReturn(null);

        AIRecommendations result = service.getUserActivityRecommendations(activityId);

        assertNull(result);
        verify(repository, times(1)).findByActivityId(activityId);
    }
}
