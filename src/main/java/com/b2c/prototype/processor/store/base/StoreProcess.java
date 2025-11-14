package com.b2c.prototype.processor.store.base;

import com.b2c.prototype.manager.store.IStoreManager;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.processor.store.IStoreProcess;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreProcess implements IStoreProcess {

    private final IStoreManager storeManager;

    public StoreProcess(IStoreManager storeManager) {
        this.storeManager = storeManager;
    }

    @Override
    public void saveStore(StoreDto storeDto) {
        storeManager.saveStore(storeDto);
    }

    @Override
    public void updateStore(Map<String, String> requestParams, StoreDto storeDto) {
        String region = requestParams.get("region");
        String storeId = requestParams.get("store");
        storeManager.updateStore(region, storeId, storeDto);
    }

    @Override
    public void deleteStore(Map<String, String> requestParams) {
        String region = requestParams.get("region");
        String storeId = requestParams.get("store");
        storeManager.deleteStore(region, storeId);
    }

    @Override
    public StoreDto getStore(Map<String, String> requestParams, String storeId) {
        String region = requestParams.get("region");
        return storeManager.getStoreByStoreId(region, storeId);
    }

    @Override
    public List<StoreDto> getAllStoresByArticularId(Map<String, String> requestParams) {
        String region = requestParams.get("region");
        String articularId = requestParams.get("articularId");
        return storeManager.getAllStoresByArticularId(region, articularId);
    }

    @Override
    public List<StoreDto> getAllStores(Map<String, String> requestParams) {
        String region = requestParams.get("region");
        String countryKey = requestParams.get("country");
        String city = requestParams.get("city");
        if (region != null && countryKey != null && city != null) {
            return storeManager.getAllStoreByRegionAndCountryAndCity(region, countryKey, city);
        }
        if (region != null && countryKey != null) {
            return storeManager.getAllStoreByRegionAndCountry(region, countryKey);
        }
        if (region != null) {
            return storeManager.getAllStoresByRegion(region);
        }
        return List.of();
    }

}
