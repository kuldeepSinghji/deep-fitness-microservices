package com.deepfitness.useractivity.repository;

import com.deepfitness.useractivity.model.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserActivityRepository extends MongoRepository<UserActivity,String> {
    List<UserActivity> findByUserId(String userId);

    String deleteUserActivityByActivityId(String userActivityId);
}
