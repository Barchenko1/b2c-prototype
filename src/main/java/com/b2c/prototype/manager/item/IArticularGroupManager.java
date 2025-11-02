package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;

import java.util.List;

public interface IArticularGroupManager {
    void saveArticularGroup(MetaDataDto metaDataDto);
    void updateArticularGroup(String itemId, MetaDataDto metaDataDto);
    void deleteArticularGroup(String itemId);

    ResponseMetaDataDto getArticularGroup(String itemId);
    List<ResponseMetaDataDto> getArticularGroupList();
}
