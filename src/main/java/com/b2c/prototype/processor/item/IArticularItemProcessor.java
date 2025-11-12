package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;

import java.util.List;
import java.util.Map;

public interface IArticularItemProcessor {
    void saveUpdateArticularItemList(Map<String, String> requestParams, List<ArticularItemDto> articularItemDtoList);
    void deleteArticularItem(Map<String, String> requestParams);

    ArticularItemDto getArticularItemDto(Map<String, String> requestParams);
    List<ArticularItemDto> getArticularItemDtoList(Map<String, String> requestParams);
    List<ArticularItemDto> getArticularItemDtoFiltered(Map<String, String> requestParams);
    List<ArticularItemDto> getArticularItemDtoSorted(Map<String, String> requestParams);
}
