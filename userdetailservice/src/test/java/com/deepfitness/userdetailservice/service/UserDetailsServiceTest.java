package com.deepfitness.userdetailservice.service;

import com.deepfitness.userdetailservice.dto.UserDetailsResponse;
import com.deepfitness.userdetailservice.dto.UserRegisterRequest;
import com.deepfitness.userdetailservice.model.UserDetails;
import com.deepfitness.userdetailservice.repository.UserDetailsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserDetailsServiceTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    @InjectMocks
    private UserDetailsService userDetailsService;

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userDetails = new UserDetails();
        userDetails.setUserId("user123");
        userDetails.setEmail("test@example.com");
        userDetails.setKeyCloakId("kc123");
    }


    @Test
    void testFindByUserId() {
        when(userDetailsRepository.findByUserId("user123")).thenReturn(userDetails);
        UserDetailsResponse result = userDetailsService.getUserDetails("user123");
        assertNotNull(result);
        assertEquals("user123", result.getUserId());
    }
    @Test
    void testExistsByUserId() {
        when(userDetailsRepository.existsByUserId("user123")).thenReturn(true);
        boolean exists = userDetailsService.validateUser("user123");
        assertTrue(exists);
    }

    @Test
    void testExistsByKeyCloakId() {
        when(userDetailsRepository.existsByKeyCloakId("kc123")).thenReturn(true);
        boolean exists = userDetailsService.validateUserByKeycloakId("kc123");
        assertTrue(exists);
    }
}
