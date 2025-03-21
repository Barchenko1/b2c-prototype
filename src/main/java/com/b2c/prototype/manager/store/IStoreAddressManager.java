package com.b2c.prototype.manager.store;

import com.b2c.prototype.modal.dto.payload.AddressDto;

public interface IStoreAddressManager {
    void saveUpdateStoreAddress(String storeId, AddressDto addressDto);

    AddressDto getResponseStoreAddressByStoreId(String storeId);
}
