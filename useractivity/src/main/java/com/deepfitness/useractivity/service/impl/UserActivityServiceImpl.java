package com.deepfitness.useractivity.service.impl;

import com.deepfitness.useractivity.dto.UserActivityRequest;
import com.deepfitness.useractivity.dto.UserActivityResponse;
import com.deepfitness.useractivity.exceptions.ResourceNotFoundException;
import com.deepfitness.useractivity.model.UserActivity;
import com.deepfitness.useractivity.repository.UserActivityRepository;
import com.deepfitness.useractivity.service.UserActivityService;
import com.deepfitness.useractivity.service.UserDetailsValidateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserActivityServiceImpl implements UserActivityService {

    private final UserActivityRepository userActivityRepository;
    private final UserDetailsValidateService userDetailsValidateService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing}")
    private String routingKey;

    @Override
    public UserActivityResponse registerUserActivity(UserActivityRequest userActivityRequest) {
        UserActivity userActivity = UserActivity.builder()
                .userId(userActivityRequest.getUserId())
                .gender(userActivityRequest.getGender())
                .userActivityType(userActivityRequest.getUserActivityType())
                .activityDuration(userActivityRequest.getActivityDuration())
                .caloriesBurned(userActivityRequest.getCaloriesBurned())
                .activityStartTime(userActivityRequest.getActivityStartTime())
                .additionalMetrics(userActivityRequest.getAdditionalMetrics())
                .build();
        UserActivity response = userActivityRepository.save(userActivity);
        try{
            rabbitTemplate.convertAndSend(exchange,routingKey,response);
        }catch (Exception ex){
            log.error("Failed to publish user activity to RabbitMQ, exception", ex);
        }
        return getUserActivityResponse(response);
    }

    @Override
    public List<UserActivityResponse> getUserActivities(String userId) {
        if(!userDetailsValidateService.validateUserDetails(userId)){
            throw new ResourceNotFoundException("User is not found with user Id: " + userId);
        }
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
        userActivityResponse.setUserActivityType(userActivity.getUserActivityType());
        userActivityResponse.setActivityStartTime(userActivity.getActivityStartTime());
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
