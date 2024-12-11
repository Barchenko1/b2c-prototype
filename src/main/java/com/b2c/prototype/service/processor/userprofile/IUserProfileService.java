package com.b2c.prototype.service.processor.userprofile;

import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.dto.request.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.request.UserDetailsDto;

public interface IUserProfileService {
    void createNewUser(RegistrationUserProfileDto registrationUserProfileDto, RoleEnum roleEnum);
    void changeUserStatusByEmail(String email, boolean isActive);
    void deleteUserProfileByEmail(String email);

    UserDetailsDto getUserProfileByUsername(String username);
    UserDetailsDto getUserByEmail(String email);
}
