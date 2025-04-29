package com.deepfitness.userdetailservice.repository;

import com.deepfitness.userdetailservice.dto.UserDetailsResponse;
import com.deepfitness.userdetailservice.dto.UserRegisterRequest;
import com.deepfitness.userdetailservice.model.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails,String> {

    UserDetailsResponse getUserDetailsByUserId(String userId);

    UserDetailsResponse saveUserDetails(UserRegisterRequest userRegisterRequest);
}
