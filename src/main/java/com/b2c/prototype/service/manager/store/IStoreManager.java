package com.b2c.prototype.service.manager.store;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;

import java.util.List;

public interface IStoreManager {
    void saveStore(StoreDto storeDto);
    void updateStore(StoreDto storeDto);
    void deleteStore(OneFieldEntityDto oneFieldEntityDto);

    ResponseStoreDto getStoreResponse(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseStoreDto> getAllStoreResponse();
}
