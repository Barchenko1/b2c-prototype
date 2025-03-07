package com.b2c.prototype.processor.address;

import com.b2c.prototype.manager.address.IAddressManager;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.response.ResponseUserAddressDto;

import java.util.List;
import java.util.Map;

public class AddressProcess implements IAddressProcess {

    private final IAddressManager addressManager;

    public AddressProcess(IAddressManager addressManager) {
        this.addressManager = addressManager;
    }

    @Override
    public void saveUpdateAddress(Map<String, String> requestParams, AddressDto addressDto) {
        String userId = requestParams.get("userId");
        String orderId = requestParams.get("orderId");
        if (userId != null || orderId == null) {
            addressManager.saveUpdateAppUserAddress(userId, addressDto);
        }
        if (userId == null && orderId != null) {
            addressManager.saveUpdateDeliveryAddress(orderId, addressDto);
        }
    }

    @Override
    public void deleteAddress(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String orderId = requestParams.get("orderId");
        if (userId != null || orderId == null) {
            addressManager.deleteAppUserAddress(userId);
        }
        if (userId == null && orderId != null) {
            addressManager.deleteDeliveryAddress(orderId);
        }
    }

    @Override
    public List<ResponseUserAddressDto> getAddressesByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return addressManager.getAddressesByUserId(userId);
    }

    @Override
    public AddressDto getAddressByOrderId(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        return addressManager.getAddressByOrderId(orderId);
    }

    @Override
    public List<AddressDto> getAddresses(Map<String, String> requestParams) {
        return addressManager.getAddresses();
    }
}
