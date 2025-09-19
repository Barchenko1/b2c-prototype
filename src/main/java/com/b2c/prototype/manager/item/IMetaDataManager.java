package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;

import java.util.List;

public interface IMetaDataManager {
    void saveMetaData(MetaDataDto metaDataDto);
    void updateMetaData(String itemId, MetaDataDto metaDataDto);
    void deleteMetaData(String itemId);

    ResponseMetaDataDto getMetaData(String itemId);
    List<ResponseMetaDataDto> getMetaDataList();
}
