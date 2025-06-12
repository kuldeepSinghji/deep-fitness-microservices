package com.deepfitness.useractivity.service.impl;

import com.deepfitness.useractivity.dto.UserActivityRequest;
import com.deepfitness.useractivity.dto.UserActivityResponse;
import com.deepfitness.useractivity.model.UserActivity;
import com.deepfitness.useractivity.repository.UserActivityRepository;
import com.deepfitness.useractivity.service.UserActivityService;
import com.deepfitness.useractivity.service.UserDetailsValidateService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import java.util.Arrays;
import java.util.List;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserActivityServiceImplTest {

    private UserActivityRepository userActivityRepository;
    private UserDetailsValidateService userDetailsValidateService;
    private UserActivityService userActivityService;

    @BeforeEach
    void setUp() {
        userActivityRepository = mock(UserActivityRepository.class);
        userDetailsValidateService = mock(UserDetailsValidateService.class);
        userActivityService = new UserActivityServiceImpl(userActivityRepository, userDetailsValidateService, null);
    }

    @Test
    void shouldSaveUserActivity() {
        UserActivity activity = UserActivity.builder()
                .userId("1")
                .activityId("LOGIN")
                .activityDuration(30)
                .caloriesBurned(300)
                .activityStartTime(LocalDateTime.now())
                .build();

        when(userActivityRepository.save(any(UserActivity.class))).thenReturn(activity);
        when(userDetailsValidateService.validateUserDetails("1")).thenReturn(true);

        UserActivityRequest request = new UserActivityRequest();
        request.setUserId("1");
        request.setActivityDuration(30);
        request.setCaloriesBurned(300);
        request.setActivityStartTime(LocalDateTime.now());

        // Call the service method under test!
        UserActivityResponse response = userActivityService.registerUserActivity(request);

        ArgumentCaptor<UserActivity> captor = ArgumentCaptor.forClass(UserActivity.class);
        verify(userActivityRepository).save(captor.capture());
        assertThat(captor.getValue().getUserId()).isEqualTo("1");
        assertThat(captor.getValue().getActivityDuration()).isEqualTo(30);
        assertThat(captor.getValue().getCaloriesBurned()).isEqualTo(300);
        assertThat(captor.getValue().getActivityStartTime()).isNotNull();

        // Optionally, assert the response
        assertThat(response).isNotNull();
        assertThat(response.getUserId()).isEqualTo("1");
        assertThat(response.getActivityDuration()).isEqualTo(30);
        assertThat(response.getCaloriesBurned()).isEqualTo(300);
    }

    @Test
    void shouldFindUserActivityById() {
        UserActivity activity = UserActivity.builder()
                .userId("1")
                .activityId("LOGIN")
                .build();

        when(userActivityRepository.findByUserId("1")).thenReturn(List.of(activity));
        when(userDetailsValidateService.validateUserDetails("1")).thenReturn(true);

        List<UserActivityResponse> found = userActivityService.getUserActivities(activity.getUserId());
        assertThat(found).isNotEmpty();
        assertThat(found.get(0).getUserId()).isEqualTo("1");
    }

    @Test
    void shouldFindAllActivitiesByUserId() {
        UserActivity activity1 = UserActivity.builder()
                .userId("2")
                .activityId("LOGIN")
                .build();
        UserActivity activity2 = UserActivity.builder()
                .userId("2")
                .activityId("LOGOUT")
                .build();

        when(userActivityRepository.findByUserId("2")).thenReturn(Arrays.asList(activity1, activity2));
        when(userDetailsValidateService.validateUserDetails("2")).thenReturn(true); // <-- Add this line

        List<UserActivityResponse> activities = userActivityService.getUserActivities("2");
        assertThat(activities).hasSize(2);
        assertThat(activities)
                .extracting(UserActivityResponse::getActivityId)
                .containsExactlyInAnyOrder("LOGIN", "LOGOUT");
    }
}
