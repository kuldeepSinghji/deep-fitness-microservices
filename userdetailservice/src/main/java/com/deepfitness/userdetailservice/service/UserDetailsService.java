package com.deepfitness.userdetailservice.service;

import com.deepfitness.userdetailservice.custom.exceptions.ResourceNotFoundException;
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
        UserDetails response = userDao.findByUserId(userId);
        if(response == null){
            throw new ResourceNotFoundException("User Not found with Id : " + userId);
        }
        return getUserDetailsResponse(response);
    }

    public UserDetailsResponse registerUserRequest(UserRegisterRequest userRegisterRequest) {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(userRegisterRequest.getEmail());
        userDetails.setFirstName(userRegisterRequest.getFirstName());
        userDetails.setGender(userRegisterRequest.getGender());
        userDetails.setLastName(userRegisterRequest.getLastName());
        userDetails.setContactNumber(userRegisterRequest.getContactNumber());
        userDetails.setPassword(userRegisterRequest.getPassword());
        UserDetails response =  userDao.save(userDetails);
        return getUserDetailsResponse(response);
    }

    public UserDetailsResponse getUserDetailsResponse(UserDetails response) {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setUserRole(String.valueOf(response.getUserRole()));
        userDetailsResponse.setUserId(response.getUserId());
        userDetailsResponse.setEmail(response.getEmail());
        userDetailsResponse.setCreateDate(response.getCreateDate());
        userDetailsResponse.setGender(response.getGender());
        userDetailsResponse.setContactNumber(response.getContactNumber());
        userDetailsResponse.setFirstName(response.getFirstName());
        userDetailsResponse.setLastName(response.getLastName());
        userDetailsResponse.setLastUpdateDate(response.getLastUpdateDate());
        return userDetailsResponse;
    }
}
