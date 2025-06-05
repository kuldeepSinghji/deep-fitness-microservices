package com.deepfitness.useractivity.repository;

import com.deepfitness.useractivity.model.UserActivity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivityRepository extends MongoRepository<UserActivity,String> {
    List<UserActivity> findByUserId(String userId);

    String deleteUserActivityByActivityId(String userActivityId);
}
