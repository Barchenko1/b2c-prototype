package com.b2c.prototype.manager.address;

import com.b2c.prototype.modal.dto.payload.AddressDto;

import java.util.List;

public interface IAddressManager {
    void saveUpdateAppUserAddress(String userId, AddressDto addressDto);
    void saveUpdateDeliveryAddress(String orderId, AddressDto addressDto);
    void deleteAppUserAddress(String userId);
    void deleteDeliveryAddress(String orderId);

    AddressDto getAddressByUserId(String userId);
    AddressDto getAddressByOrderId(String orderId);

    List<AddressDto> getAddresses();
}
