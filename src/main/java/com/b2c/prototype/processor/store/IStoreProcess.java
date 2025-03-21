package com.b2c.prototype.processor.store;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;

import java.util.List;
import java.util.Map;

public interface IStoreProcess {
    void saveStore(StoreDto storeDto);
    void updateStore(Map<String, String> requestParams, StoreDto storeDto);
    void deleteStore(Map<String, String> requestParams);

    ResponseStoreDto getStore(Map<String, String> requestParams, String storeId);
    List<ResponseStoreDto> getAllResponseStoresByArticularId(Map<String, String> requestParams);
    List<ResponseStoreDto> getAllResponseStores(Map<String, String> requestParams);
}
