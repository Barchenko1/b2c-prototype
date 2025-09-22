package com.b2c.prototype.processor.store.base;

import com.b2c.prototype.manager.store.IStoreAddressManager;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.processor.store.IStoreAddressProcess;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StoreAddressProcess implements IStoreAddressProcess {

    private final IStoreAddressManager storeAddressManager;

    public StoreAddressProcess(IStoreAddressManager storeAddressManager) {
        this.storeAddressManager = storeAddressManager;
    }

    @Override
    public void saveUpdateStoreAddress(Map<String, String> requestParams, AddressDto addressDto) {
        String storeId = requestParams.get("storeId");
        storeAddressManager.saveUpdateStoreAddress(storeId, addressDto);
    }

    @Override
    public AddressDto getResponseStoreAddress(Map<String, String> requestParams) {
        String storeId = requestParams.get("storeId");
        return storeAddressManager.getResponseStoreAddressByStoreId(storeId);
    }
}
