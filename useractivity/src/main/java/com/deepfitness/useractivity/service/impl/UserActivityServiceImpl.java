package com.deepfitness.useractivity.service.impl;

import com.deepfitness.useractivity.dto.UserActivityRequest;
import com.deepfitness.useractivity.dto.UserActivityResponse;
import com.deepfitness.useractivity.exceptions.ResourceNotFoundException;
import com.deepfitness.useractivity.model.UserActivity;
import com.deepfitness.useractivity.repository.UserActivityRepository;
import com.deepfitness.useractivity.service.UserActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository userActivityRepository;

    @Override
    public UserActivityResponse registerUserActivity(UserActivityRequest userActivityRequest) {
        UserActivity userActivity = UserActivity.builder()
                .userId(userActivityRequest.getUserId())
                .userActivityType(userActivityRequest.getUserActivityType())
                .activityDuration(userActivityRequest.getActivityDuration())
                .caloriesBurned(userActivityRequest.getCaloriesBurned())
                .activityStartTime(userActivityRequest.getActivityStartTime())
                .additionalMetrics(userActivityRequest.getAdditionalMetrics())
                .build();
        UserActivity response = userActivityRepository.save(userActivity);
        return getUserActivityResponse(response);
    }

    @Override
    public List<UserActivityResponse> getUserActivities(String userId) {
        List<UserActivity> userActivities =  userActivityRepository.findByUserId(userId);
        return userActivities.stream().map(this::getUserActivityResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserActivityResponse getUserActivity(String userActivityId) {
        Optional<UserActivity> userActivity = userActivityRepository.findById(userActivityId);
        userActivity.orElseThrow(()->
            new ResourceNotFoundException("User Activity not present with activity Id: " + userActivityId)
        );
        return getUserActivityResponse(userActivity.orElse(null));
    }

    private UserActivityResponse getUserActivityResponse(UserActivity userActivity){
        UserActivityResponse userActivityResponse = new UserActivityResponse();
        userActivityResponse.setActivityId(userActivity.getActivityId());
        userActivityResponse.setUserId(userActivity.getUserId());
        userActivityResponse.setActivityDuration(userActivity.getActivityDuration());
        userActivityResponse.setCaloriesBurned(userActivity.getCaloriesBurned());
        userActivityResponse.setGender(userActivity.getGender());
        userActivityResponse.setAdditionalMetrics(userActivity.getAdditionalMetrics());
        userActivityResponse.setCreateDate(userActivity.getCreateDate());
        userActivityResponse.setLastUpdateDate(userActivity.getLastUpdateDate());
        return userActivityResponse;
    }
}
