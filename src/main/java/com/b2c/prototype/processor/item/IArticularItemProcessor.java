package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.response.ResponseArticularItemDto;

import java.util.List;
import java.util.Map;

public interface IArticularItemProcessor {
    void saveUpdateArticularItemList(Map<String, String> requestParams, List<ArticularItemDto> articularItemDtoList);
    void deleteArticularItem(Map<String, String> requestParams);

    ResponseArticularItemDto getResponseArticularItemDto(Map<String, String> requestParams);
    List<ResponseArticularItemDto> getResponseArticularItemDtoList(Map<String, String> requestParams);
    List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered(Map<String, String> requestParams);
    List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(Map<String, String> requestParams);
}
