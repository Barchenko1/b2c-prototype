package com.b2c.prototype.manager.address;

import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;

import java.util.List;

public interface IUserAddressManager {
    void saveUserAddress(String userId, UserAddressDto addressDto);
    void updateUserAddress(String userId, String addressId, UserAddressDto addressDto);
    void setDefaultUserCreditCard(String userId, String addressId);
    void deleteUserAddress(String userId, String addressId);

    UserAddressDto getDefaultUserAddress(String userId);
    List<UserAddressDto> getUserAddressesByUserId(String userId);
    List<AddressDto> getAllAddressesByAddressId(String addressId);

}
