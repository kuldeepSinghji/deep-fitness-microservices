package com.deepfitness.gateway.service;

import com.deepfitness.gateway.dto.UserDetailsResponse;
import com.deepfitness.gateway.dto.UserRegisterRequest;
import reactor.core.publisher.Mono;

public interface UserDetailsService {
    Mono<Boolean> validateUserDetails(String userId);

    Mono<UserDetailsResponse> registerUserDetails(UserRegisterRequest userRegisterRequest);
}
