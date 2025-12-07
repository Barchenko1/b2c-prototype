package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;

import java.util.List;

public interface IStoreArticularGroupManager {
    void saveStoreArticularGroup(StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void updateStoreArticularGroup(String tenantId, String articularGroupId, StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void deleteStoreArticularGroup(String tenantId, String articularGroupId, boolean isForced);

    StoreArticularGroupResponseDto getStoreArticularGroup(String tenantId, String articularGroupId);
    List<StoreArticularGroupResponseDto> getStoreArticularGroups(String tenantId);
}
