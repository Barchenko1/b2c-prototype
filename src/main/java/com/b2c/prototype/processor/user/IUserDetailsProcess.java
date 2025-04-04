package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserDetailsDto;

import java.util.List;
import java.util.Map;

public interface IUserDetailsProcess {
    void createNewUser(Map<String, String> requestParams, RegistrationUserDetailsDto registrationUserDetailsDto);
    void saveUserDetails(Map<String, String> requestParams, UserDetailsDto userDetailsDto);
    void updateUserDetailsByUserId(Map<String, String> requestParams, UserDetailsDto userDetailsDto);
    void updateUserStatusByUserId(Map<String, String> requestParams);
    void updateUserVerifyEmailByUserId(Map<String, String> requestParams);
    void updateUserVerifyPhoneByUserId(Map<String, String> requestParams);
    void deleteUserDetailsByUserId(Map<String, String> requestParams);

    ResponseUserDetailsDto getUserDetailsByUserId(Map<String, String> requestParams);
    List<ResponseUserDetailsDto> getUserDetails(Map<String, String> requestParams);
}
