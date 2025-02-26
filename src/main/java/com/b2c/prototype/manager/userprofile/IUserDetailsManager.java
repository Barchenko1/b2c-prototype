package com.b2c.prototype.manager.userprofile;

import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;

import java.util.List;

public interface IUserDetailsManager {
    void createNewUser(RegistrationUserProfileDto registrationUserProfileDto);
    void updateUserDetailsByUserId(String userId, UserProfileDto userProfileDto);
    void updateUserStatusByUserId(String userId, boolean status);
    void deleteUserProfileByUserId(String userId);

    UserProfileDto getUserProfileByUserId(String userId);
    List<UserProfileDto> getUserProfiles();
}
