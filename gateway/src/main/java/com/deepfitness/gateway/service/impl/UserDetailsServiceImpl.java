package com.deepfitness.gateway.service.impl;

import com.deepfitness.gateway.dto.UserDetailsResponse;
import com.deepfitness.gateway.dto.UserRegisterRequest;
import com.deepfitness.gateway.exceptions.ResourceNotFoundException;
import com.deepfitness.gateway.service.UserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final WebClient userServiceWebClient;

    @Override
    public Mono<Boolean> validateUserDetails(String userId) {
            return userServiceWebClient.get()
                    .uri("/api/users/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .onErrorResume(WebClientResponseException.class, e -> {
                        if(e.getStatusCode() == HttpStatus.NOT_FOUND) return Mono.error(new ResourceNotFoundException("User not found with userId " + userId));
                        return Mono.error(new Exception("Unexpected Error"));
                    });
        }

    @Override
    public Mono<UserDetailsResponse> registerUserDetails(UserRegisterRequest userRegisterRequest) {
        log.info("Calling the registerUserDetails from api-gateway keycloakId: " + userRegisterRequest.getKeyCloakId());
        return userServiceWebClient.post()
                .uri("/api/users/register-user")
                .bodyValue(userRegisterRequest)
                .retrieve()
                .bodyToMono(UserDetailsResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {
                    if(e.getStatusCode() == HttpStatus.NOT_FOUND) return Mono.error(new ResourceNotFoundException("User not found with userId " + userRegisterRequest.getEmail()));
                    return Mono.error(new Exception("Unexpected Error"));
                });
    }
}
