package com.b2c.prototype.processor.store;

import com.b2c.prototype.modal.dto.payload.AddressDto;

import java.util.Map;

public interface IStoreAddressProcess {
    void saveUpdateStoreAddress(Map<String, String> requestParams, AddressDto addressDto);

    AddressDto getResponseStoreAddress(Map<String, String> requestParams);
}
