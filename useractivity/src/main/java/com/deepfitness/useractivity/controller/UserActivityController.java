package com.deepfitness.useractivity.controller;

import com.deepfitness.useractivity.dto.UserActivityRequest;
import com.deepfitness.useractivity.dto.UserActivityResponse;
import com.deepfitness.useractivity.service.UserActivityService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/activity")
@AllArgsConstructor
public class UserActivityController {

    UserActivityService userActivityService;

    @PostMapping("/register-user-activity")
    public ResponseEntity<UserActivityResponse> registerUserActivity(@RequestBody UserActivityRequest userActivityRequest){
        return ResponseEntity.ok(userActivityService.registerUserActivity(userActivityRequest));
    }

    @GetMapping("/user-activities")
    public ResponseEntity<List<UserActivityResponse>> getUserActivities(@NonNull @RequestHeader("X-USER-ID") String userId){
        return ResponseEntity.ok(userActivityService.getUserActivities(userId));
    }

    @GetMapping("{userActivityId}")
    public ResponseEntity<UserActivityResponse> getUserActivity(@NonNull @PathVariable String userActivityId){
        return ResponseEntity.ok(userActivityService.getUserActivity(userActivityId));
    }

    @DeleteMapping("{userActivityId}")
    public ResponseEntity<String > removeUserActivity(@NonNull @PathVariable String userActivityId){
        return ResponseEntity.ok(userActivityService.removeUserActivity(userActivityId));
    }
}
