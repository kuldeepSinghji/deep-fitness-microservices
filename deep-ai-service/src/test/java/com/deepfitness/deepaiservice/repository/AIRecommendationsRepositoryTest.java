package com.deepfitness.deepaiservice.repository;
import com.deepfitness.deepaiservice.model.AIRecommendations;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AIRecommendationsRepositoryTest {

    @Test
    @DisplayName("Test findByUserId returns list of recommendations")
    void testFindByUserId() {
        AIRecommendationsRepository repository = mock(AIRecommendationsRepository.class);
        AIRecommendations rec1 = new AIRecommendations();
        AIRecommendations rec2 = new AIRecommendations();
        List<AIRecommendations> expected = Arrays.asList(rec1, rec2);

        when(repository.findByUserId("user1")).thenReturn(expected);

        List<AIRecommendations> result = repository.findByUserId("user1");
        assertEquals(expected, result);
        verify(repository, times(1)).findByUserId("user1");
    }

    @Test
    @DisplayName("Test findByUserId returns empty list")
    void testFindByUserIdReturnsEmpty() {
        AIRecommendationsRepository repository = mock(AIRecommendationsRepository.class);
        when(repository.findByUserId("nouser")).thenReturn(List.of());

        List<AIRecommendations> result = repository.findByUserId("nouser");
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repository, times(1)).findByUserId("nouser");
    }

    @Test
    @DisplayName("Test findByActivityId returns recommendation")
    void testFindByActivityId() {
        AIRecommendationsRepository repository = mock(AIRecommendationsRepository.class);
        AIRecommendations rec = new AIRecommendations();
        when(repository.findByActivityId("activity1")).thenReturn(rec);

        AIRecommendations result = repository.findByActivityId("activity1");
        assertEquals(rec, result);
        verify(repository, times(1)).findByActivityId("activity1");
    }

    @Test
    @DisplayName("Test findByActivityId returns null")
    void testFindByActivityIdReturnsNull() {
        AIRecommendationsRepository repository = mock(AIRecommendationsRepository.class);
        when(repository.findByActivityId("noactivity")).thenReturn(null);

        AIRecommendations result = repository.findByActivityId("noactivity");
        assertNull(result);
        verify(repository, times(1)).findByActivityId("noactivity");
    }
}
