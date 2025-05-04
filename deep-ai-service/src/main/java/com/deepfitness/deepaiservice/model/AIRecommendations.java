package com.deepfitness.deepaiservice.model;

import com.deepfitness.deepaiservice.enums.UserActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "ai-recommendations")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AIRecommendations {

    @Id
    private String id;
    private String activityId;
    private String userId;
    private UserActivityType activityType;
    private String recommendations;
    private String fullAnalysisPerformanceMetrics;
    private List<String> nextWorkoutSuggestions;
    private List<String> areasToImprove;
    private List<String> safetyGuidelines;
    @CreatedDate
    private LocalDateTime createDate;
}
