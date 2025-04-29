package com.deepfitness.userdetailservice.service;

import com.deepfitness.userdetailservice.dto.UserDetailsResponse;
import com.deepfitness.userdetailservice.dto.UserRegisterRequest;
import com.deepfitness.userdetailservice.model.UserDetails;
import com.deepfitness.userdetailservice.repository.UserDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsService {

    UserDetailsRepository userDao;

    public UserDetailsResponse getUserDetails(String userId) {
        return userDao.getUserDetailsByUserId(userId);
    }

    public UserDetailsResponse registerUserRequest(UserRegisterRequest userRegisterRequest) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(userRegisterRequest.getEmail());
        userDetails.setFirstName(userRegisterRequest.getFirstName());
        userDetails.setGender(userRegisterRequest.getGender());
        userDetails.setLastName(userRegisterRequest.getLastName());
        userDetails.setContactNumber(userRegisterRequest.getContactNumber());
        userDetails.setPassword(userRegisterRequest.getPassword());
        return userDao.saveUserDetails(userRegisterRequest);
    }
}
