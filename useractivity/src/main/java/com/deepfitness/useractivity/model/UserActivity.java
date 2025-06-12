package com.deepfitness.useractivity.model;

import com.deepfitness.useractivity.enums.UserActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;

@Document(collection = "user-activities")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserActivity {

    @Id
    private String activityId;
    private String userId;
    private UserActivityType userActivityType;
    private Integer activityDuration;
    private Integer caloriesBurned;
    private LocalDateTime activityStartTime;
    @CreatedDate
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime lastUpdateDate;
    private String gender;
    private Map<String,Object> additionalMetrics;
}
