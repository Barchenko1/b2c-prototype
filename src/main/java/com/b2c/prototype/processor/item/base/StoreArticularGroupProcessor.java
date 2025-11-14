package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.item.IArticularGroupManager;
import com.b2c.prototype.manager.item.IStoreArticularGroupManager;
import com.b2c.prototype.modal.dto.payload.general.StoreArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.processor.item.IArticularGroupProcessor;
import com.b2c.prototype.processor.item.IStoreArticularGroupProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StoreArticularGroupProcessor implements IStoreArticularGroupProcessor {

    private final IStoreArticularGroupManager storeArticularGroupManager;

    public StoreArticularGroupProcessor(IStoreArticularGroupManager storeArticularGroupManager) {
        this.storeArticularGroupManager = storeArticularGroupManager;
    }


    @Override
    public void saveStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupDto articularGroupDto) {
        storeArticularGroupManager.saveStoreArticularGroup(articularGroupDto);
    }

    @Override
    public void updateStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupDto articularGroupDto) {
        String region = requestParams.get("region");
        String articularGroupId = requestParams.get("articularGroupId");
        storeArticularGroupManager.updateStoreArticularGroup(region, articularGroupId, articularGroupDto);
    }

    @Override
    public void deleteStoreArticularGroup(Map<String, String> requestParams) {
        String region = requestParams.get("region");
        String articularGroupId = requestParams.get("articularGroupId");
        storeArticularGroupManager.deleteStoreArticularGroup(region, articularGroupId);
    }

    @Override
    public StoreArticularGroupDto getStoreArticularGroup(Map<String, String> requestParams) {
        return null;
    }

    @Override
    public List<StoreArticularGroupDto> getStoreArticularGroupList(Map<String, String> requestParams) {
        return List.of();
    }
}
