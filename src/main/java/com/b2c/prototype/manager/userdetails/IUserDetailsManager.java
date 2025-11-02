package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsAddCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsRemoveCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsStatusDto;

import java.util.List;

public interface IUserDetailsManager {
    void createNewUser(RegistrationUserDetailsDto registrationUserDetailsDto);
    void updateUserDetailsByUserId(UserDetailsDto userDetailsDto);
    void updateUserDetailsContactInfo(UserDetailsContactInfoDto userDetailsContactInfoDto);

    void updateUserDetailsStatus(UserDetailsStatusDto userDetailsStatusDto);
    void updateUserDetailsVerifyEmail(UserDetailsStatusDto userDetailsStatusDto);
    void updateUserDetailsVerifyPhone(UserDetailsStatusDto userDetailsStatusDto);
    void deleteUserDetailsByUserId(String userId);

    void addUserDetailsAddress(UserDetailsAddCollectionDto userDetailsAddCollectionDto);
    void addUserDetailsCreditCard(UserDetailsAddCollectionDto userDetailsAddCollectionDto);
    void addUserDetailsDevice(UserDetailsAddCollectionDto userDetailsAddCollectionDto);

    void deleteUserDetailsAddress(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto);
    void deleteUserDetailsCreditCard(UserDetailsRemoveCollectionDto userDetailsRemoveCollectionDto);

    UserDetailsDto getUserDetailsByUserId(String userId);
    List<UserDetailsDto> getUserDetails();
}
