package com.deepfitness.userdetailservice.controller;

import com.deepfitness.userdetailservice.dto.UserDetailsResponse;
import com.deepfitness.userdetailservice.dto.UserRegisterRequest;
import com.deepfitness.userdetailservice.service.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthController authController;

    private UserDetailsResponse userDetailsResponse;
    private UserRegisterRequest userRegisterRequest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUserId("user123");
        userDetailsResponse.setEmail("test@example.com");

        userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setEmail("test@example.com");
        userRegisterRequest.setPassword("password123");
        userRegisterRequest.setFirstName("John");
        userRegisterRequest.setLastName("Doe");
    }

    @Test
    void testGetUserDetails() {
        when(userDetailsService.getUserDetails("user123")).thenReturn(userDetailsResponse);
        ResponseEntity<UserDetailsResponse> response = authController.getUserDetails("user123");
        assertEquals(200, response.getStatusCode().value());
        assertEquals("user123", response.getBody().getUserId());
    }

    @Test
    void testRegisterUserDetails() throws Exception {
        when(userDetailsService.registerUserRequest(any(UserRegisterRequest.class))).thenReturn(userDetailsResponse);
        ResponseEntity<UserDetailsResponse> response = authController.registerUserDetails(userRegisterRequest);
        assertEquals(200, response.getStatusCode().value());
        assertEquals("test@example.com", response.getBody().getEmail());
    }

    @Test
    void testValidateUserDetails() {
        when(userDetailsService.validateUserByKeycloakId("kc123")).thenReturn(true);
        ResponseEntity<Boolean> response = authController.validateUserDetails("kc123");
        assertEquals(200, response.getStatusCode().value());
        assertTrue(response.getBody());
    }
}
