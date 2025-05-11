package com.deepfitness.gateway.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRegisterRequest {
    private String firstName;
    private String lastName;
    private String keyCloakId;
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be atleast 6 characters")
    private String password;
    private String gender;
    private Integer contactNumber;
}
