package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;

import java.util.List;
import java.util.Map;

public interface IUserDetailsProcess {
    void createNewUser(Map<String, String> requestParams, RegistrationUserProfileDto registrationUserProfileDto);
    void updateUserDetailsByUserId(Map<String, String> requestParams, UserProfileDto userProfileDto);
    void updateUserStatusByUserId(Map<String, Object> requestParams);
    void deleteUserProfileByUserId(Map<String, String> requestParams);

    UserProfileDto getUserProfileByUserId(Map<String, String> requestParams);
    List<UserProfileDto> getUserProfiles(Map<String, String> requestParams);
}
