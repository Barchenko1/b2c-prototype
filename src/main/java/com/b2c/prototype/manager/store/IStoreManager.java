package com.b2c.prototype.manager.store;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;

import java.util.List;

public interface IStoreManager {
    void saveStore(StoreDto storeDto);
    void updateStore(String region, String storeId, StoreDto storeDto);
    void deleteStore(String region, String storeId);

    StoreDto getStoreByStoreId(String region, String storeId);
    List<StoreDto> getAllStoresByRegion(String region);
    List<StoreDto> getAllStoresByArticularId(String region, String articularId);
    List<StoreDto> getAllStoreByRegionAndCountry(String region, String countryName);
    List<StoreDto> getAllStoreByRegionAndCountryAndCity(String region, String countryName, String cityName);
}
