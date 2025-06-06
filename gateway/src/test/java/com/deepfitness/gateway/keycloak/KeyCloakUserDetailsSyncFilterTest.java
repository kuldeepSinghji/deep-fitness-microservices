package com.deepfitness.gateway.keycloak;

import com.deepfitness.gateway.service.UserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class KeyCloakUserDetailsSyncFilterTest {

    private UserDetailsService userDetailsService;
    private KeyCloakUserDetailsSyncFilter filter;
    private WebFilterChain chain;

    @BeforeEach
    void setUp() {
        userDetailsService = mock(UserDetailsService.class);
        filter = new KeyCloakUserDetailsSyncFilter(userDetailsService);
        chain = mock(WebFilterChain.class);
        when(chain.filter(any())).thenReturn(Mono.empty());
    }

    private String createDummyJwt() {
        String headerJson = "{\"alg\":\"none\",\"typ\":\"JWT\"}";
        String payloadJson = "{\"sub\":\"user123\",\"email\":\"test@example.com\",\"given_name\":\"John\",\"family_name\":\"Doe\"}";
        String header = Base64.getUrlEncoder().withoutPadding().encodeToString(headerJson.getBytes(StandardCharsets.UTF_8));
        String payload = Base64.getUrlEncoder().withoutPadding().encodeToString(payloadJson.getBytes(StandardCharsets.UTF_8));
        return "Bearer " + header + "." + payload + ".";
    }

    @Test
    void testFilter_UserExists() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/")
                .header("X-User-Id", "user123")
                .header("Authorization", createDummyJwt())
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        when(userDetailsService.validateUserDetails("user123")).thenReturn(Mono.just(true));

        StepVerifier.create(filter.filter(exchange, chain))
                .verifyComplete();

        verify(userDetailsService).validateUserDetails("user123");
        verify(userDetailsService, never()).registerUserDetails(any());
    }

    @Test
    void testFilter_MissingHeaders() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/").build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        StepVerifier.create(filter.filter(exchange, chain))
                .verifyComplete();

        verify(userDetailsService, never()).validateUserDetails(anyString());
        verify(userDetailsService, never()).registerUserDetails(any());
    }

    @Test
    void testFilter_InvalidToken() {
        MockServerHttpRequest request = MockServerHttpRequest.get("/")
                .header("Authorization", "Bearer invalid.token")
                .build();
        MockServerWebExchange exchange = MockServerWebExchange.from(request);

        StepVerifier.create(filter.filter(exchange, chain))
                .verifyComplete();

        verify(userDetailsService, never()).validateUserDetails(anyString());
        verify(userDetailsService, never()).registerUserDetails(any());
    }
}