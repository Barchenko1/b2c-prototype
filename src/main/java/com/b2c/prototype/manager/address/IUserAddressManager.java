package com.b2c.prototype.manager.address;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;

import java.util.List;

public interface IUserAddressManager {
    void saveUserAddress(String userId, UserAddressDto addressDto);
    void updateUserAddress(String userId, String addressId, UserAddressDto addressDto);
    void setDefaultUserCreditCard(String userId, String addressId);
    void deleteUserAddress(String userId, String addressId);

    ResponseUserAddressDto getDefaultUserAddress(String userId);
    List<ResponseUserAddressDto> getUserAddressesByUserId(String userId);
    List<AddressDto> getAllAddressesByAddressId(String addressId);

}
