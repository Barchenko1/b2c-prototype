package com.b2c.prototype.processor.store;

import com.b2c.prototype.manager.store.IStoreManager;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.b2c.prototype.modal.dto.payload.store.ResponseStoreDto;
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
        String storeId = requestParams.get("storeId");
        storeManager.updateStore(storeId, storeDto);
    }

    @Override
    public void deleteStore(Map<String, String> requestParams) {
        String storeId = requestParams.get("storeId");
        storeManager.deleteStore(storeId);
    }

    @Override
    public ResponseStoreDto getStore(Map<String, String> requestParams, String storeId) {
        return storeManager.getResponseStoreByStoreId(storeId);
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStoresByArticularId(Map<String, String> requestParams) {
        String articularId = requestParams.get("articularId");
        return storeManager.getAllResponseStoresByArticularId(articularId);
    }

    @Override
    public List<ResponseStoreDto> getAllResponseStores(Map<String, String> requestParams) {
        String countryName = requestParams.get("country");
        String cityName = requestParams.get("city");
        if (countryName != null && cityName != null) {
            return storeManager.getAllResponseStoreByCountryAndCity(countryName, cityName);
        }
        if (countryName != null) {
            return storeManager.getAllResponseStoreByCountry(countryName);
        }
        return List.of();
    }

}
