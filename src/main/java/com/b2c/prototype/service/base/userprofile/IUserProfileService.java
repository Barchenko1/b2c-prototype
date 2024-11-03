package com.b2c.prototype.service.base.userprofile;

import com.b2c.prototype.modal.dto.response.ResponseUserDataDto;
import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.dto.request.RegistrationUserProfileDto;

public interface IUserProfileService {
    void createNewUser(RegistrationUserProfileDto registrationUserProfileDto, RoleEnum roleEnum);
    void changeUserStatusByLogin(String login, boolean isActive);
    void changeUserStatusByEmail(String email, boolean isActive);
    void deleteUserByLogin(String login);
    void deleteUserByEmail(String email);

    ResponseUserDataDto getUserByEmail(String email);

}
