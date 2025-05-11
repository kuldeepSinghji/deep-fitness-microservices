package com.deepfitness.gateway.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDetailsResponse {
    private String keyCloakId;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDateTime createDate;
    private LocalDateTime lastUpdateDate;
    private String userRole;
    private String gender;
    private Integer contactNumber;
}
