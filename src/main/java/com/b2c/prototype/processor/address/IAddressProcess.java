package com.b2c.prototype.processor.address;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;

import java.util.List;
import java.util.Map;

public interface IAddressProcess {
    void saveUpdateAddress(Map<String, String> requestParams, AddressDto addressDto);
    void deleteAddress(Map<String, String> requestParams);

    List<ResponseUserAddressDto> getAddressesByUserId(Map<String, String> requestParams);
    AddressDto getAddressByOrderId(Map<String, String> requestParams);
    List<AddressDto> getAddresses(Map<String, String> requestParams);
}
