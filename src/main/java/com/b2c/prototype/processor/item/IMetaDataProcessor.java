package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseItemDataDto;

import java.util.List;
import java.util.Map;

public interface IMetaDataProcessor {
    void saveMetaData(Map<String, String> requestParams, ItemDataDto itemDataDto);
    void updateMetaData(Map<String, String> requestParams, ItemDataDto itemDataDto);
    void deleteMetaData(Map<String, String> requestParams);

    ResponseItemDataDto getMetaData(Map<String, String> requestParams);
    List<ResponseItemDataDto> getMetaDataList(Map<String, String> requestParams);
}
