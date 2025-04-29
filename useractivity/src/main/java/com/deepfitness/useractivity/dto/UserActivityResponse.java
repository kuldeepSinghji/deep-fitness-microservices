package com.deepfitness.useractivity.dto;

import com.deepfitness.useractivity.enums.UserActivityType;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.Map;

@Data
public class UserActivityResponse {
    private String activityId;
    private String userId;
    private UserActivityType userActivityType;
    private Integer activityDuration;
    private Integer caloriesBurned;
    private LocalDateTime activityStartTime;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private Map<String,Object> additionalMetrics;
    private String gender;
}
