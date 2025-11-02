package com.b2c.prototype.processor.user.base;

import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsAddCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsRemoveCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsStatusDto;
import com.b2c.prototype.processor.user.IUserDetailsProcess;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserDetailsProcess implements IUserDetailsProcess {

    private final ObjectMapper objectMapper;
    private final IUserDetailsManager userDetailsManager;

    public UserDetailsProcess(ObjectMapper objectMapper,
                              IUserDetailsManager userDetailsManager) {
        this.objectMapper = objectMapper;
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public void createNewUser(Map<String, String> requestParams, RegistrationUserDetailsDto registrationUserDetailsDto) {
        userDetailsManager.createNewUser(registrationUserDetailsDto);
    }

    @Override
    public void updateUserDetailsByUserId(Map<String, String> requestParams, UserDetailsDto userDetailsDto) {
        userDetailsManager.updateUserDetailsByUserId(userDetailsDto);
    }

    @Override
    public void updateUserDetailsContactInfo(UserDetailsContactInfoDto userDetailsContactInfoDto) {
        userDetailsManager.updateUserDetailsContactInfo(userDetailsContactInfoDto);
    }

    @Override
    public void updateUserDetailsStatus(UserDetailsStatusDto userDetailsStatusDto) {
        userDetailsManager.updateUserDetailsStatus(userDetailsStatusDto);
    }

    @Override
    public void updateUserDetailsVerifyEmail(UserDetailsStatusDto userDetailsStatusDto) {
        userDetailsManager.updateUserDetailsVerifyEmail(userDetailsStatusDto);
    }

    @Override
    public void updateUserDetailsVerifyPhone(UserDetailsStatusDto userDetailsStatusDto) {
        userDetailsManager.updateUserDetailsVerifyPhone(userDetailsStatusDto);
    }

    @Override
    public void deleteUserDetailsByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        userDetailsManager.deleteUserDetailsByUserId(userId);
    }

    @Override
    public void addUserDetailsAddress(UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        userDetailsManager.addUserDetailsAddress(userDetailsAddCollectionDto);
    }

    @Override
    public void addUserDetailsCreditCard(UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        userDetailsManager.addUserDetailsCreditCard(userDetailsAddCollectionDto);
    }

    @Override
    public void addUserDetailsDevice(UserDetailsAddCollectionDto userDetailsAddCollectionDto) {
        userDetailsManager.addUserDetailsDevice(userDetailsAddCollectionDto);
    }

    @Override
    public void deleteUserDetailsAddress(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto) {
        userDetailsManager.deleteUserDetailsAddress(userDetailsRemoveCollectionDto);
    }

    @Override
    public void deleteUserDetailsCreditCard(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto) {
        userDetailsManager.deleteUserDetailsCreditCard(userDetailsRemoveCollectionDto);
    }

    @Override
    public UserDetailsDto getUserDetailsByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return userDetailsManager.getUserDetailsByUserId(userId);
    }

    @Override
    public List<UserDetailsDto> getUserDetails(Map<String, String> requestParams) {
        return userDetailsManager.getUserDetails();
    }
}
