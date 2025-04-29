package com.deepfitness.useractivity.service;

import com.deepfitness.useractivity.dto.UserActivityRequest;
import com.deepfitness.useractivity.dto.UserActivityResponse;

import java.util.List;

public interface UserActivityService {
    UserActivityResponse registerUserActivity(UserActivityRequest userActivityRequest);
    List<UserActivityResponse> getUserActivities(String userId);
    UserActivityResponse getUserActivity(String userActivityId);
}
