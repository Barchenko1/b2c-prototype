package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsAddCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsRemoveCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsStatusDto;

import java.util.List;
import java.util.Map;

public interface IUserDetailsProcess {
    void createNewUser(Map<String, String> requestParams, RegistrationUserDetailsDto registrationUserDetailsDto);
    void updateUserDetailsByUserId(Map<String, String> requestParams, UserDetailsDto userDetailsDto);
    void updateUserDetailsContactInfo(UserDetailsContactInfoDto userDetailsContactInfoDto);

    void updateUserDetailsStatus(UserDetailsStatusDto userDetailsStatusDto);
    void updateUserDetailsVerifyEmail(UserDetailsStatusDto userDetailsStatusDto);
    void updateUserDetailsVerifyPhone(UserDetailsStatusDto userDetailsStatusDto);
    void deleteUserDetailsByUserId(Map<String, String> requestParams);

    void addUserDetailsAddress(UserDetailsAddCollectionDto userDetailsAddCollectionDto);
    void addUserDetailsCreditCard(UserDetailsAddCollectionDto userDetailsAddCollectionDto);
    void addUserDetailsDevice(UserDetailsAddCollectionDto userDetailsAddCollectionDto);

    void deleteUserDetailsAddress(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto);
    void deleteUserDetailsCreditCard(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto);

    UserDetailsDto getUserDetailsByUserId(Map<String, String> requestParams);
    List<UserDetailsDto> getUserDetails(Map<String, String> requestParams);
}
