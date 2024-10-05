package com.b2c.prototype.service.base.appuser;

import com.b2c.prototype.modal.dto.response.ResponseUserDataDto;
import com.b2c.prototype.modal.constant.RoleEnum;
import com.b2c.prototype.modal.dto.request.RegistrationAppUserDto;

public interface IAppUserService {
    void createNewUser(RegistrationAppUserDto registrationAppUserDto, RoleEnum roleEnum);
    void changeUserStatusByLogin(String login, boolean isActive);
    void changeUserStatusByEmail(String email, boolean isActive);
    void deleteUserByLogin(String login);
    void deleteUserByEmail(String email);

    ResponseUserDataDto getUserByEmail(String email);

}
