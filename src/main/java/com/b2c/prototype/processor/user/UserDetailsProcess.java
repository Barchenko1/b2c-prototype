package com.b2c.prototype.processor.user;

import com.b2c.prototype.manager.userdetails.IUserDetailsManager;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserDetailsDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserDetailsProcess implements IUserDetailsProcess {

    private final IUserDetailsManager userDetailsManager;

    public UserDetailsProcess(IUserDetailsManager userDetailsManager) {
        this.userDetailsManager = userDetailsManager;
    }

    @Override
    public void createNewUser(Map<String, String> requestParams, RegistrationUserDetailsDto registrationUserDetailsDto) {
        userDetailsManager.createNewUser(registrationUserDetailsDto);
    }

    @Override
    public void saveUserDetails(Map<String, String> requestParams, UserDetailsDto userDetailsDto) {
        userDetailsManager.saveUserDetails(userDetailsDto);
    }

    @Override
    public void updateUserDetailsByUserId(Map<String, String> requestParams, UserDetailsDto userDetailsDto) {
        String userId = requestParams.get("userId");
        userDetailsManager.updateUserDetailsByUserId(userId, userDetailsDto);
    }

    @Override
    public void updateUserStatusByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        boolean status = Boolean.parseBoolean(requestParams.get("status"));
        userDetailsManager.updateUserStatusByUserId(userId, status);
    }

    @Override
    public void updateUserVerifyEmailByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        boolean status = Boolean.parseBoolean(requestParams.get("verifyEmail"));
        userDetailsManager.updateUserVerifyEmailByUserId(userId, status);
    }

    @Override
    public void updateUserVerifyPhoneByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        boolean status = Boolean.parseBoolean(requestParams.get("verifyPhone"));
        userDetailsManager.updateUserVerifyPhoneByUserId(userId, status);
    }

    @Override
    public void deleteUserDetailsByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        userDetailsManager.deleteUserDetailsByUserId(userId);
    }

    @Override
    public ResponseUserDetailsDto getUserDetailsByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return userDetailsManager.getUserDetailsByUserId(userId);
    }

    @Override
    public List<ResponseUserDetailsDto> getUserDetails(Map<String, String> requestParams) {
        return userDetailsManager.getResponseUserDetails();
    }
}
