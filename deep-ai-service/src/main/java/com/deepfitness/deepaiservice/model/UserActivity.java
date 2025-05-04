package com.deepfitness.deepaiservice.model;
import com.deepfitness.deepaiservice.enums.UserActivityType;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.Map;

@Data
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
