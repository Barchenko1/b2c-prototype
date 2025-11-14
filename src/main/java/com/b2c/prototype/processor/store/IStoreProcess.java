package com.b2c.prototype.processor.store;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;

import java.util.List;
import java.util.Map;

public interface IStoreProcess {
    void saveStore(StoreDto storeDto);
    void updateStore(Map<String, String> requestParams, StoreDto storeDto);
    void deleteStore(Map<String, String> requestParams);

    StoreDto getStore(Map<String, String> requestParams, String storeId);
    List<StoreDto> getAllStoresByArticularId(Map<String, String> requestParams);
    List<StoreDto> getAllStores(Map<String, String> requestParams);
}
