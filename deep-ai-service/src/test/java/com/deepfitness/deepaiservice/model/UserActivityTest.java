package com.deepfitness.deepaiservice.model;
import com.deepfitness.deepaiservice.enums.UserActivityType;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class UserActivityTest {

    @Test
    void testNoArgsConstructorAndSetters() {
        UserActivity activity = new UserActivity();
        activity.setActivityId("a1");
        activity.setUserId("u1");
        activity.setUserActivityType(UserActivityType.RUNNING);
        activity.setActivityDuration(45);
        activity.setCaloriesBurned(300);
        LocalDateTime start = LocalDateTime.now();
        activity.setActivityStartTime(start);
        LocalDateTime created = LocalDateTime.now();
        activity.setCreateDate(created);
        LocalDateTime updated = LocalDateTime.now();
        activity.setLastUpdateDate(updated);
        activity.setGender("male");
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("distance", 5.0);
        activity.setAdditionalMetrics(metrics);

        assertEquals("a1", activity.getActivityId());
        assertEquals("u1", activity.getUserId());
        assertEquals(UserActivityType.RUNNING, activity.getUserActivityType());
        assertEquals(45, activity.getActivityDuration());
        assertEquals(300, activity.getCaloriesBurned());
        assertEquals(start, activity.getActivityStartTime());
        assertEquals(created, activity.getCreateDate());
        assertEquals(updated, activity.getLastUpdateDate());
        assertEquals("male", activity.getGender());
        assertEquals(metrics, activity.getAdditionalMetrics());
    }

    @Test
    void testEqualsAndHashCode() {
        UserActivity activity1 = new UserActivity();
        activity1.setActivityId("a2");
        activity1.setUserId("u2");

        UserActivity activity2 = new UserActivity();
        activity2.setActivityId("a2");
        activity2.setUserId("u2");

        assertEquals(activity1, activity2);
        assertEquals(activity1.hashCode(), activity2.hashCode());
    }

    @Test
    void testToString() {
        UserActivity activity = new UserActivity();
        String str = activity.toString();
        assertNotNull(str);
        assertTrue(str.contains("UserActivity"));
    }
}
