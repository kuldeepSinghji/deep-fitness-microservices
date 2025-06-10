package com.deepfitness.userdetailservice.repository;

import com.deepfitness.userdetailservice.model.UserDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class UserDetailsRepositoryTest {

    @Mock
    private UserDetailsRepository userDetailsRepository;

    private UserDetails user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new UserDetails();
        user.setUserId("user123");
        user.setEmail("test@example.com");
        user.setKeyCloakId("kc123");
    }

    @Test
    void testFindByUserId() {
        when(userDetailsRepository.findByUserId("user123")).thenReturn(user);
        UserDetails found = userDetailsRepository.findByUserId("user123");
        assertNotNull(found);
        assertEquals("user123", found.getUserId());
    }

    @Test
    void testExistsByUserId() {
        when(userDetailsRepository.existsByUserId("user123")).thenReturn(true);
        assertTrue(userDetailsRepository.existsByUserId("user123"));
        when(userDetailsRepository.existsByUserId("user999")).thenReturn(false);
        assertFalse(userDetailsRepository.existsByUserId("user999"));
    }

    @Test
    void testExistsByEmail() {
        when(userDetailsRepository.existsByEmail("test@example.com")).thenReturn(true);
        assertTrue(userDetailsRepository.existsByEmail("test@example.com"));
        when(userDetailsRepository.existsByEmail("notfound@example.com")).thenReturn(false);
        assertFalse(userDetailsRepository.existsByEmail("notfound@example.com"));
    }

    @Test
    void testFindByEmail() {
        when(userDetailsRepository.findByEmail("test@example.com")).thenReturn(user);
        UserDetails found = userDetailsRepository.findByEmail("test@example.com");
        assertNotNull(found);
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void testExistsByKeyCloakId() {
        when(userDetailsRepository.existsByKeyCloakId("kc123")).thenReturn(true);
        assertTrue(userDetailsRepository.existsByKeyCloakId("kc123"));
        when(userDetailsRepository.existsByKeyCloakId("kc999")).thenReturn(false);
        assertFalse(userDetailsRepository.existsByKeyCloakId("kc999"));
    }
}