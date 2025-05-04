package com.deepfitness.useractivity.service.impl;

import com.deepfitness.useractivity.exceptions.ResourceNotFoundException;
import com.deepfitness.useractivity.service.UserDetailsValidateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
public class UserDetailsValidateServiceImpl implements UserDetailsValidateService {

    private final WebClient userServiceWebClient;

    @Override
    public boolean validateUserDetails(String userId) {
        try{
            return Boolean.TRUE.equals(userServiceWebClient.get()
                    .uri("/api/users/validate/{userId}", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block());
        }catch (WebClientResponseException ex){
            if(ex.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR){
                throw new ResourceNotFoundException("Internal Server Error with userId: " + userId);
            }else if(ex.getStatusCode() == HttpStatus.BAD_REQUEST){
                throw new ResourceNotFoundException("Bad Request with userId: " + userId);
            }
        }
        return false;
    }
}
