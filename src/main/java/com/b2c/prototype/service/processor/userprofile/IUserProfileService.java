package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.dto.payload.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.payload.UserProfileDto;

import java.util.List;

public interface IUserProfileService {
    void createNewUser(RegistrationUserProfileDto registrationUserProfileDto);
    void updateUserStatusByUserId(String userId, boolean isActive);
    void deleteUserProfileByUserId(String userId);

    UserProfileDto getUserProfileByUserId(String userId);
    List<UserProfileDto> getUserProfiles();
}
