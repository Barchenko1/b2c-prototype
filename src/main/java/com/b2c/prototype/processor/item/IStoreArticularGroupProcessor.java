package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.StoreArticularGroupRequestDto;

import java.util.List;
import java.util.Map;

public interface IStoreArticularGroupProcessor {
    void saveStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void updateStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void deleteStoreArticularGroup(Map<String, String> requestParams);

    StoreArticularGroupRequestDto getStoreArticularGroup(Map<String, String> requestParams);
    List<StoreArticularGroupRequestDto> getStoreArticularGroupList(Map<String, String> requestParams);
}
