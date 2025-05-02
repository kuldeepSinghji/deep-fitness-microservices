package com.deepfitness.deepaiservice.model;

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
    private String activityType;
    private String recommendations;
    private List<String> suggestions;
    private List<String> improvements;
    private List<String> safety;
    @CreatedDate
    private LocalDateTime createDate;
}
