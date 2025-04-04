package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseItemDataDto;

import java.util.List;
import java.util.Map;

public interface IItemDataProcessor {
    void saveItemData(Map<String, String> requestParams, ItemDataDto itemDataDto);
    void updateItemData(Map<String, String> requestParams, ItemDataDto itemDataDto);
    void deleteItemData(Map<String, String> requestParams);

    ResponseItemDataDto getItemData(Map<String, String> requestParams);
    List<ResponseItemDataDto> getItemDataList(Map<String, String> requestParams);
}
