package com.b2c.prototype.processor.address;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.UserAddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;

import java.util.List;
import java.util.Map;

public interface IUserAddressProcess {
    void saveUpdateUserAddressByUserId(Map<String, String> requestParams, UserAddressDto userAddressDto);
    void deleteUserAddress(Map<String, String> requestParams);
    void setDefaultAddress(Map<String, String> requestParams);
    ResponseUserAddressDto getDefaultUserAddress(Map<String, String> requestParams);
    List<ResponseUserAddressDto> getUserAddressListByUserId(Map<String, String> requestParams);
    List<AddressDto> getAllAddressesByAddress(Map<String, String> requestParams);
}
