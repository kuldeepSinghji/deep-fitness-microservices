package com.deepfitness.userdetailservice.model;

import com.deepfitness.userdetailservice.enums.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsTest {

    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        userDetails = new UserDetails();
        userDetails.setUserId("user123");
        userDetails.setFirstName("John");
        userDetails.setLastName("Doe");
        userDetails.setEmail("john.doe@example.com");
        userDetails.setPassword("password123");
        userDetails.setGender("Male");
        userDetails.setContactNumber(1234567890);
        userDetails.setKeyCloakId("kc123");
        userDetails.setCreateDate(LocalDateTime.now());
        userDetails.setLastUpdateDate(LocalDateTime.now());
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("user123", userDetails.getUserId());
        assertEquals("John", userDetails.getFirstName());
        assertEquals("Doe", userDetails.getLastName());
        assertEquals("john.doe@example.com", userDetails.getEmail());
        assertEquals("password123", userDetails.getPassword());
        assertEquals("Male", userDetails.getGender());
        assertEquals(1234567890, userDetails.getContactNumber());
        assertEquals("kc123", userDetails.getKeyCloakId());
        assertNotNull(userDetails.getCreateDate());
        assertNotNull(userDetails.getLastUpdateDate());
    }

    @Test
    void testUserRoleIsAlwaysUser() {
        assertEquals(UserRole.USER, userDetails.getUserRole());
    }
}
