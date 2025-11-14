package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.general.StoreArticularGroupDto;

import java.util.List;

public interface IStoreArticularGroupManager {
    void saveStoreArticularGroup(StoreArticularGroupDto articularGroupDto);
    void updateStoreArticularGroup(String region, String articularGroupId, StoreArticularGroupDto articularGroupDto);
    void deleteStoreArticularGroup(String region, String articularGroupId);

    StoreArticularGroupDto getStoreArticularGroup(String itemId);
    List<StoreArticularGroupDto> getAStorerticularGroupList();
}
