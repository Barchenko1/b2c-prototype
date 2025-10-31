package com.b2c.prototype.processor.user.base;

import com.b2c.prototype.manager.address.IUserAddressManager;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.processor.user.IUserAddressProcess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserAddressProcess implements IUserAddressProcess {

    private final IUserAddressManager userAddressManager;

    public UserAddressProcess(IUserAddressManager userAddressManager) {
        this.userAddressManager = userAddressManager;
    }

    @Override
    public void saveUpdateUserAddressByUserId(Map<String, String> requestParams, UserAddressDto userAddressDto) {
        String userId = requestParams.get("userId");
        String addressId = requestParams.get("addressId");
        if (addressId != null) {
            userAddressManager.updateUserAddress(userId, addressId, userAddressDto);
        } else {
            userAddressManager.saveUserAddress(userId, userAddressDto);
        }
    }

    @Override
    public void deleteUserAddress(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String addressId = requestParams.get("addressId");
        userAddressManager.deleteUserAddress(userId, addressId);
    }

    @Override
    public void setDefaultAddress(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String addressId = requestParams.get("addressId");
        userAddressManager.setDefaultUserCreditCard(userId, addressId);
    }

    @Override
    public UserAddressDto getDefaultUserAddress(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return userAddressManager.getDefaultUserAddress(userId);
    }

    @Override
    public List<UserAddressDto> getUserAddressListByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return userAddressManager.getUserAddressesByUserId(userId);
    }

    @Override
    public List<AddressDto> getAllAddressesByAddress(Map<String, String> requestParams) {
        String addressId = requestParams.get("addressId");
        return userAddressManager.getAllAddressesByAddressId(addressId);
    }
}
