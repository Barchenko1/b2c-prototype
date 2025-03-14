package com.b2c.prototype.manager.store;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;

import java.util.List;

public interface IStoreAddressManager {
    void saveUpdateStoreAddress(String storeId, AddressDto addressDto);

    AddressDto getResponseStoreAddressByStoreId(String storeId);
}
