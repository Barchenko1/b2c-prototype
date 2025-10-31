package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;

import java.util.List;

public interface IUserDetailsManager {
    void createNewUser(RegistrationUserDetailsDto registrationUserDetailsDto);
    void updateUserDetailsByUserId(String userId, UserDetailsDto userDetailsDto);
    void updateUserStatusByUserId(String userId, boolean status);
    void updateUserVerifyEmailByUserId(String userId, boolean verifyEmail);
    void updateUserVerifyPhoneByUserId(String userId, boolean verifyPhone);
    void deleteUserDetailsByUserId(String userId);

    UserDetailsDto getUserDetailsByUserId(String userId);
    List<UserDetailsDto> getUserDetails();
}
