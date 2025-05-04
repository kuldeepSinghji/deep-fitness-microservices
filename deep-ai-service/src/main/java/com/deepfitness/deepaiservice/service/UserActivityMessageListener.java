package com.deepfitness.deepaiservice.service;


import com.deepfitness.deepaiservice.model.UserActivity;

public interface UserActivityMessageListener {
    void processUserActivity(UserActivity userActivity);
}
