package com.b2c.prototype.manager.store;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.response.ResponseStoreDto;

import java.util.List;

public interface IStoreManager {
    void saveStore(StoreDto storeDto);
    void updateStore(String storeId, StoreDto storeDto);
    void deleteStore(String storeId);

    ResponseStoreDto getResponseStoreByStoreId(String storeId);
    List<ResponseStoreDto> getAllResponseStoresByArticularId(String articularId);
    List<ResponseStoreDto> getAllResponseStoreByCountry(String countryName);
    List<ResponseStoreDto> getAllResponseStoreByCountryAndCity(String countryName, String cityName);
}
