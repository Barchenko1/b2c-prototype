package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.general.StoreArticularGroupDto;

import java.util.List;
import java.util.Map;

public interface IStoreArticularGroupProcessor {
    void saveStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupDto articularGroupDto);
    void updateStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupDto articularGroupDto);
    void deleteStoreArticularGroup(Map<String, String> requestParams);

    StoreArticularGroupDto getStoreArticularGroup(Map<String, String> requestParams);
    List<StoreArticularGroupDto> getStoreArticularGroupList(Map<String, String> requestParams);
}
