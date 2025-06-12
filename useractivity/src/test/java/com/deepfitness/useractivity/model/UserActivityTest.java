package com.deepfitness.useractivity.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class UserActivityTest {

    @Test
    void builderShouldCreateUserActivityWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        UserActivity activity = UserActivity.builder()
                .activityId("abc123")
                .userId("user1")
                .activityDuration(60)
                .caloriesBurned(500)
                .activityStartTime(now)
                .build();

        assertThat(activity.getActivityId()).isEqualTo("abc123");
        assertThat(activity.getUserId()).isEqualTo("user1");
        assertThat(activity.getActivityDuration()).isEqualTo(60);
        assertThat(activity.getCaloriesBurned()).isEqualTo(500);
        assertThat(activity.getActivityStartTime()).isEqualTo(now);
    }

    @Test
    void shouldSetAndGetFields() {
        UserActivity activity = new UserActivity();
        activity.setActivityId("id1");
        activity.setUserId("user2");
        activity.setActivityDuration(30);
        activity.setCaloriesBurned(200);
        LocalDateTime time = LocalDateTime.now();
        activity.setActivityStartTime(time);

        assertThat(activity.getActivityId()).isEqualTo("id1");
        assertThat(activity.getUserId()).isEqualTo("user2");
        assertThat(activity.getActivityDuration()).isEqualTo(30);
        assertThat(activity.getCaloriesBurned()).isEqualTo(200);
        assertThat(activity.getActivityStartTime()).isEqualTo(time);
    }

    @Test
    void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        UserActivity activity1 = UserActivity.builder()
                .activityId("idX")
                .userId("userX")
                .activityId("A1")
                .activityDuration(10)
                .caloriesBurned(100)
                .activityStartTime(now)
                .build();

        UserActivity activity2 = UserActivity.builder()
                .activityId("idX")
                .userId("userX")
                .activityId("A1")
                .activityDuration(10)
                .caloriesBurned(100)
                .activityStartTime(now)
                .build();

        assertThat(activity1).isEqualTo(activity2);
        assertThat(activity1.hashCode()).isEqualTo(activity2.hashCode());
    }

    @Test
    void testToString() {
        UserActivity activity = UserActivity.builder()
                .userId("userY")
                .activityId("A2")
                .activityDuration(20)
                .caloriesBurned(150)
                .activityStartTime(LocalDateTime.now())
                .build();

        assertThat(activity.toString()).contains("userY", "A2");
    }
}
