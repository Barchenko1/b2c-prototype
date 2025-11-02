package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;

import java.util.List;
import java.util.Map;

public interface IArticularGroupProcessor {
    void saveArticularGroup(Map<String, String> requestParams, MetaDataDto metaDataDto);
    void updateArticularGroup(Map<String, String> requestParams, MetaDataDto metaDataDto);
    void deleteArticularGroup(Map<String, String> requestParams);

    ResponseMetaDataDto getArticularGroup(Map<String, String> requestParams);
    List<ResponseMetaDataDto> getArticularGroupList(Map<String, String> requestParams);
}
