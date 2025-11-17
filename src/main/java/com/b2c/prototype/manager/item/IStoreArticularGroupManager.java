package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.item.StoreArticularGroupRequestDto;

import java.util.List;

public interface IStoreArticularGroupManager {
    void saveStoreArticularGroup(StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void updateStoreArticularGroup(String region, String articularGroupId, StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void deleteStoreArticularGroup(String region, String articularGroupId);

    StoreArticularGroupRequestDto getStoreArticularGroup(String articularGroupId);
    List<StoreArticularGroupRequestDto> getAStorerticularGroupList();
}
