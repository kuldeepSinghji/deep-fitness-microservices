package com.deepfitness.useractivity.service.impl;

import com.deepfitness.useractivity.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class UserDetailsValidateServiceImplTest {

    private WebClient userServiceWebClient;
    private UserDetailsValidateServiceImpl userDetailsValidateService;

    @BeforeEach
    void setUp() {
        userServiceWebClient = mock(WebClient.class, RETURNS_DEEP_STUBS);
        userDetailsValidateService = new UserDetailsValidateServiceImpl(userServiceWebClient);
    }

    @Test
    void shouldReturnTrueWhenUserIsValid() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(userServiceWebClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(String.class), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.just(true));

        boolean result = userDetailsValidateService.validateUserDetails("123");
        assertThat(result).isTrue();
    }

    @Test
    void shouldReturnFalseWhenUserIsInvalid() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(userServiceWebClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(String.class), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenReturn(Mono.just(false));

        boolean result = userDetailsValidateService.validateUserDetails("456");
        assertThat(result).isFalse();
    }

    @Test
    void shouldThrowResourceNotFoundExceptionOnInternalServerError() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(userServiceWebClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(String.class), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenThrow(
                new WebClientResponseException("Internal Server Error", 500, "Internal Server Error", null, null, null)
        );

        assertThatThrownBy(() -> userDetailsValidateService.validateUserDetails("789"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Internal Server Error with userId: 789");
    }

    @Test
    void shouldThrowResourceNotFoundExceptionOnBadRequest() {
        WebClient.RequestHeadersUriSpec uriSpec = mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = mock(WebClient.ResponseSpec.class);

        when(userServiceWebClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(any(String.class), any(Object[].class))).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Boolean.class)).thenThrow(
                new WebClientResponseException("Bad Request", 400, "Bad Request", null, null, null)
        );

        assertThatThrownBy(() -> userDetailsValidateService.validateUserDetails("999"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Bad Request with userId: 999");
    }
}
