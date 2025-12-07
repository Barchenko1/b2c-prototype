package com.b2c.prototype.manager.store;

import com.b2c.prototype.modal.dto.payload.store.StoreDto;

import java.util.List;

public interface IStoreManager {
    void saveStore(StoreDto storeDto);
    void updateStore(String tenantId, String storeId, StoreDto storeDto);
    void deleteStore(String tenantId, String storeId);

    StoreDto getStoreByStoreId(String tenantId, String storeId);
    List<StoreDto> getAllStoresByRegion(String region);
    List<StoreDto> getAllStoresByArticularId(String tenantId, String articularId);
    List<StoreDto> getAllStoreByRegionAndCountry(String tenantId, String countryName);
    List<StoreDto> getAllStoreByRegionAndCountryAndCity(String tenantId, String countryName, String cityName);
}
