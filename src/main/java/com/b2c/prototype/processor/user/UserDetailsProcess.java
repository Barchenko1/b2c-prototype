package com.b2c.prototype.processor.user;

import com.b2c.prototype.manager.userprofile.IUserDetailsManager;
import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;

import java.util.List;
import java.util.Map;

public class UserDetailsProcess implements IUserDetailsProcess {

    private final IUserDetailsManager userProfileManager;

    public UserDetailsProcess(IUserDetailsManager userProfileManager) {
        this.userProfileManager = userProfileManager;
    }

    @Override
    public void createNewUser(Map<String, String> requestParams, RegistrationUserProfileDto registrationUserProfileDto) {
        userProfileManager.createNewUser(registrationUserProfileDto);
    }

    @Override
    public void updateUserDetailsByUserId(Map<String, String> requestParams, UserProfileDto userProfileDto) {
        String userId = requestParams.get("userId");
        userProfileManager.updateUserDetailsByUserId(userId, userProfileDto);
    }

    @Override
    public void updateUserStatusByUserId(Map<String, Object> requestParams) {
        String userId = (String) requestParams.get("userId");
        boolean status = (boolean) requestParams.get("status");
        userProfileManager.updateUserStatusByUserId(userId, status);
    }

    @Override
    public void deleteUserProfileByUserId(Map<String, String> requestParams) {

    }

    @Override
    public UserProfileDto getUserProfileByUserId(Map<String, String> requestParams) {
        return null;
    }

    @Override
    public List<UserProfileDto> getUserProfiles(Map<String, String> requestParams) {
        return List.of();
    }
}
