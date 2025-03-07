package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.UserDetailsDto;
import com.b2c.prototype.modal.dto.response.ResponseUserDetailsDto;

import java.util.List;

public interface IUserDetailsManager {
    void createNewUser(RegistrationUserDetailsDto registrationUserDetailsDto);
    void saveUserDetails(UserDetailsDto userDetailsDto);
    void updateUserDetailsByUserId(String userId, UserDetailsDto userDetailsDto);
    void updateUserStatusByUserId(String userId, boolean status);
    void updateUserVerifyEmailByUserId(String userId, boolean verifyEmail);
    void updateUserVerifyPhoneByUserId(String userId, boolean verifyPhone);
    void deleteUserDetailsByUserId(String userId);

    ResponseUserDetailsDto getUserDetailsByUserId(String userId);
    List<ResponseUserDetailsDto> getResponseUserDetails();
}
