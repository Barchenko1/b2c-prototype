package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularGroupRequestDto;
import com.b2c.prototype.modal.dto.payload.item.request.StoreArticularItemRequestDto;
import com.b2c.prototype.modal.dto.payload.item.response.StoreArticularGroupResponseDto;

import java.util.List;
import java.util.Map;

public interface IStoreArticularGroupProcessor {
    void saveStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void updateStoreArticularGroup(Map<String, String> requestParams, StoreArticularGroupRequestDto storeArticularGroupRequestDto);
    void deleteStoreArticularGroup(Map<String, String> requestParams);

    void addStoreArticularItem(Map<String, String> requestParams, StoreArticularItemRequestDto storeArticularItemRequestDto);
    void deleteStoreArticularItem(Map<String, String> requestParams);

    StoreArticularGroupResponseDto getStoreArticularGroup(Map<String, String> requestParams);
    List<StoreArticularGroupResponseDto> getStoreArticularGroupList(Map<String, String> requestParams);
}
