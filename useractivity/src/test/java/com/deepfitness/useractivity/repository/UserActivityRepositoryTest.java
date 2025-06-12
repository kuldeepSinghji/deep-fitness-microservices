package com.deepfitness.useractivity.repository;

import com.deepfitness.useractivity.model.UserActivity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.lang.reflect.Field;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
public class UserActivityRepositoryTest {

    @Autowired
    private UserActivityRepository userActivityRepository;

    @Test
    @DisplayName("Should save and retrieve UserActivity by ID")
    void saveAndFindById() throws NoSuchFieldException, IllegalAccessException {
        UserActivity activity = UserActivity.builder()
            .userId("1")
            .activityId("LOGIN")
            .build();

        UserActivity saved = userActivityRepository.save(activity);

        Field idField = saved.getClass().getDeclaredField("activityId");
        idField.setAccessible(true);
        Object idValue = idField.get(saved);

        Optional<UserActivity> found = userActivityRepository.findById((String) idValue);
        assertThat(found).isPresent();
        assertThat(found.get().getUserId()).isEqualTo("1");
        assertThat(found.get().getActivityId()).isEqualTo("LOGIN");
    }

    @Test
    @DisplayName("Should find all activities for a user")
    void findByUserId() {
        UserActivity activity1 = UserActivity.builder()
            .userId("2")
            .activityId("LOGIN")
            .build();

        UserActivity activity2 = UserActivity.builder()
            .userId("2")
            .activityId("LOGOUT")
            .build();

        userActivityRepository.save(activity1);
        userActivityRepository.save(activity2);

        var activities = userActivityRepository.findByUserId("2");
        assertThat(activities).hasSize(2);
        assertThat(activities)
            .extracting(UserActivity::getActivityId)
            .containsExactlyInAnyOrder("LOGIN", "LOGOUT");
    }
}
