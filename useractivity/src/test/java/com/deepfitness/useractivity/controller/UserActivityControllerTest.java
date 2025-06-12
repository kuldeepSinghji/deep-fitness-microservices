package com.deepfitness.useractivity.controller;

import com.deepfitness.useractivity.dto.UserActivityRequest;
import com.deepfitness.useractivity.dto.UserActivityResponse;
import com.deepfitness.useractivity.service.UserActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

class UserActivityControllerTest {

    private MockMvc mockMvc;
    private UserActivityService userActivityService;

    @BeforeEach
    void setUp() {
        userActivityService = mock(UserActivityService.class);
        UserActivityController controller = new UserActivityController(userActivityService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldRegisterUserActivity() throws Exception {
        UserActivityRequest request = new UserActivityRequest();
        request.setUserId("1");
        request.setActivityDuration(30);
        request.setCaloriesBurned(300);
        request.setActivityStartTime(LocalDateTime.now());

        UserActivityResponse response = new UserActivityResponse();
        response.setUserId("1");
        response.setActivityDuration(30);
        response.setCaloriesBurned(300);
        response.setActivityStartTime(request.getActivityStartTime());

        when(userActivityService.registerUserActivity(any(UserActivityRequest.class))).thenReturn(response);

        String json = String.format(
            "{" +
                "\"userId\":\"1\"," +
                "\"activityId\":\"LOGIN\"," +
                "\"activityDuration\":30," +
                "\"caloriesBurned\":300," +
                "\"activityStartTime\":\"%s\"" +
            "}", request.getActivityStartTime().toString()
        );

        mockMvc.perform(post("/api/activity/register-user-activity")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value("1"))
                .andExpect(jsonPath("$.activityDuration").value(30))
                .andExpect(jsonPath("$.caloriesBurned").value(300));
    }

    @Test
    void shouldGetUserActivities() throws Exception {
        UserActivityResponse response = new UserActivityResponse();
        response.setUserId("1");
        response.setActivityId("LOGIN");
        response.setActivityDuration(30);
        response.setCaloriesBurned(300);
        response.setActivityStartTime(LocalDateTime.now());

        when(userActivityService.getUserActivities("1")).thenReturn(List.of(response));

        mockMvc.perform(get("/api/activity/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
