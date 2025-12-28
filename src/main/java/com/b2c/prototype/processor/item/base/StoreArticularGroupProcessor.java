package com.b2c.prototype.processor.item.base;

import com.b2c.prototype.manager.item.IStoreArticularGroupManager;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularItemRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;
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
    public void saveStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupRequestDto articularGroupDto) {
        storeArticularGroupManager.saveStoreArticularGroup(articularGroupDto);
    }

    @Override
    public void updateStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupRequestDto articularGroupDto) {
        String tenant = requestParams.get("tenant");
        String articularGroupId = requestParams.get("articularGroupId");
        storeArticularGroupManager.updateStoreArticularGroup(tenant, articularGroupId, articularGroupDto);
    }

    @Override
    public void deleteStoreArticularGroup(Map<String, String> requestParams) {
        String tenant = requestParams.get("tenant");
        String articularGroupId = requestParams.get("articularGroupId");
        boolean isForced = Boolean.parseBoolean(requestParams.get("isForced"));
        storeArticularGroupManager.deleteStoreArticularGroup(tenant, articularGroupId, isForced);
    }

    @Override
    public void addStoreArticularItem(Map<String, String> requestParams, StoreArticularItemRequestDto storeArticularItemRequestDto) {
        storeArticularGroupManager.addStoreArticularItem(storeArticularItemRequestDto);
    }

    @Override
    public void deleteStoreArticularItem(Map<String, String> requestParams) {
        String tenant = requestParams.get("tenant");
        String articularGroupId = requestParams.get("articularGroupId");
        String articularId = requestParams.get("articularId");
        boolean isForced = Boolean.parseBoolean(requestParams.get("isForced"));
        storeArticularGroupManager.deleteStoreArticularItem(tenant, articularGroupId, articularId, isForced);
        String test = "test";
    }

    @Override
    public StoreArticularGroupResponseDto getStoreArticularGroup(Map<String, String> requestParams) {
        String tenant = requestParams.get("tenant");
        String articularGroupId = requestParams.get("articularGroupId");
        return storeArticularGroupManager.getStoreArticularGroup(tenant, articularGroupId);
    }

    @Override
    public List<StoreArticularGroupResponseDto> getStoreArticularGroupList(Map<String, String> requestParams) {
        String tenant = requestParams.get("tenant");
        return storeArticularGroupManager.getStoreArticularGroups(tenant);
    }
}
