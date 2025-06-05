package com.deepfitness.userdetailservice.controller;

import com.deepfitness.userdetailservice.dto.UserDetailsResponse;
import com.deepfitness.userdetailservice.dto.UserRegisterRequest;
import com.deepfitness.userdetailservice.service.UserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/users")
@AllArgsConstructor
@Slf4j
public class AuthController {

    UserDetailsService userDetailsService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetailsResponse> getUserDetails(@PathVariable String userId){
        return ResponseEntity.ok(userDetailsService.getUserDetails(userId));
    }

    @PostMapping("/register-user")
    public ResponseEntity<UserDetailsResponse> registerUserDetails(@Valid @RequestBody UserRegisterRequest userRegisterRequest) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        log.info("Serialized payload: {}", mapper.writeValueAsString(userRegisterRequest));
        return ResponseEntity.ok(userDetailsService.registerUserRequest(userRegisterRequest));
    }

    @GetMapping("/validate/{userId}")
    public ResponseEntity<Boolean> validateUserDetails(@PathVariable String userId){
        return ResponseEntity.ok(userDetailsService.validateUserByKeycloakId(userId));
    }
}
