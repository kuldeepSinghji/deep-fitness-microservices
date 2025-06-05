package com.deepfitness.userdetailservice.service;

import com.deepfitness.userdetailservice.custom.exceptions.ResourceNotFoundException;
import com.deepfitness.userdetailservice.dto.UserDetailsResponse;
import com.deepfitness.userdetailservice.dto.UserRegisterRequest;
import com.deepfitness.userdetailservice.model.UserDetails;
import com.deepfitness.userdetailservice.repository.UserDetailsRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
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
        if(userDao.existsByEmail(userRegisterRequest.getEmail())){
            UserDetails userDetails = userDao.findByEmail(userRegisterRequest.getEmail());
            return getUserDetailsResponse(userDetails);
        }
        UserDetails userDetails = getUserDetails(userRegisterRequest);
        UserDetails response =  userDao.save(userDetails);
        return getUserDetailsResponse(response);
    }

    private UserDetails getUserDetails(UserRegisterRequest userRegisterRequest) {
        log.info("keyCloak Id: userdetails " + userRegisterRequest.getKeyCloakId());
        UserDetails userDetails = new UserDetails();
        userDetails.setKeyCloakId(userRegisterRequest.getKeyCloakId());
        userDetails.setEmail(userRegisterRequest.getEmail());
        userDetails.setFirstName(userRegisterRequest.getFirstName());
        userDetails.setGender(userRegisterRequest.getGender());
        userDetails.setLastName(userRegisterRequest.getLastName());
        userDetails.setContactNumber(userRegisterRequest.getContactNumber());
        userDetails.setPassword(userRegisterRequest.getPassword());
        return userDetails;
    }

    public UserDetailsResponse getUserDetailsResponse(UserDetails response) {
        UserDetailsResponse userDetailsResponse = new UserDetailsResponse();
        userDetailsResponse.setKeyCloakId(userDetailsResponse.getKeyCloakId());
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

    public Boolean validateUser(String userId) {
        log.info("Validating the userId: " + userId);
        return userDao.existsByUserId(userId);
    }

    public Boolean validateUserByKeycloakId(String keycloakId){
        log.info("Validating the keycloakId: " + keycloakId);
        return userDao.existsByKeyCloakId(keycloakId);
    }

}
