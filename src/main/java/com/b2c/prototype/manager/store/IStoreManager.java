package com.b2c.prototype.manager.store;

import com.b2c.prototype.modal.dto.payload.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;

import java.util.List;

public interface IStoreManager {
    void saveStore(StoreDto storeDto);
    void updateStore(StoreDto storeDto);
    void deleteStore(String articularId);

    ResponseStoreDto getStoreResponse(String articularId);
    List<ResponseStoreDto> getAllStoreResponse();
}
