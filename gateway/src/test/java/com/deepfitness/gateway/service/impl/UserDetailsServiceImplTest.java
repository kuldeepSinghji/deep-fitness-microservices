package com.deepfitness.gateway.service.impl;

import com.deepfitness.gateway.dto.UserDetailsResponse;
import com.deepfitness.gateway.dto.UserRegisterRequest;
import com.deepfitness.gateway.exceptions.ResourceNotFoundException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


class UserDetailsServiceImplTest {

    private WebClient webClient;
    private UserDetailsServiceImpl userDetailsService;

@BeforeEach
void setUp() {
    webClient = mock(WebClient.class);
    userDetailsService = new UserDetailsServiceImpl(webClient);
}

// Dummy JWT generator for testing
private String createDummyJwt() {
    // Return a simple dummy JWT string (not a real JWT)
    return "dummy.jwt.token";
}

    @Test
    void validateUserDetails_success() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString(), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.just(true));

        StepVerifier.create(userDetailsService.validateUserDetails("user123"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void validateUserDetails_notFound() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString(), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        WebClientResponseException notFound = WebClientResponseException.create(
                HttpStatus.NOT_FOUND.value(), "Not Found", null, null, null);

        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.error(notFound));

        StepVerifier.create(userDetailsService.validateUserDetails("user123"))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }

    @Test
    void registerUserDetails_success() {
        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(uriSpec);
        when(uriSpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec) headersSpec); // Cast to raw type to avoid generic mismatch
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        UserDetailsResponse response = new UserDetailsResponse();
        when(responseSpec.bodyToMono(UserDetailsResponse.class)).thenReturn(Mono.just(response));

        UserRegisterRequest request = new UserRegisterRequest();
        StepVerifier.create(userDetailsService.registerUserDetails(request))
                .expectNext(response)
                .verifyComplete();
    }

    @Test
    void registerUserDetails_notFound() {
        WebClient.RequestBodyUriSpec uriSpec = mock(WebClient.RequestBodyUriSpec.class);
        WebClient.RequestHeadersSpec<?> headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(webClient.post()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(uriSpec);
        when(uriSpec.bodyValue(any())).thenReturn((WebClient.RequestHeadersSpec) headersSpec); // Cast to raw type to avoid generic mismatch
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        WebClientResponseException notFound = WebClientResponseException.create(
                HttpStatus.NOT_FOUND.value(), "Not Found", null, null, null);

        when(responseSpec.bodyToMono(UserDetailsResponse.class)).thenReturn(Mono.error(notFound));

        UserRegisterRequest request = new UserRegisterRequest();
        StepVerifier.create(userDetailsService.registerUserDetails(request))
                .expectError(ResourceNotFoundException.class)
                .verify();
    }
}