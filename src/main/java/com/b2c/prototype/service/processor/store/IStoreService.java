package com.b2c.prototype.service.processor.store;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;

import java.util.List;

public interface IStoreService {
    void saveStore(StoreDto storeDto);
    void updateStore(StoreDto storeDto);
    void deleteStore(OneFieldEntityDto oneFieldEntityDto);

    ResponseStoreDto getStoreResponse(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseStoreDto> getAllStoreResponse();
}
