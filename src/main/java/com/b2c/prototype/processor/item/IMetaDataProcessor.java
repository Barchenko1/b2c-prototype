package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;

import java.util.List;
import java.util.Map;

public interface IMetaDataProcessor {
    void saveMetaData(Map<String, String> requestParams, MetaDataDto metaDataDto);
    void updateMetaData(Map<String, String> requestParams, MetaDataDto metaDataDto);
    void deleteMetaData(Map<String, String> requestParams);

    ResponseMetaDataDto getMetaData(Map<String, String> requestParams);
    List<ResponseMetaDataDto> getMetaDataList(Map<String, String> requestParams);
}
