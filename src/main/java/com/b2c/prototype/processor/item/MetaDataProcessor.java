package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.MetaDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseMetaDataDto;
import com.b2c.prototype.manager.item.IMetaDataManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MetaDataProcessor implements IMetaDataProcessor {

    private final IMetaDataManager itemDataManager;

    public MetaDataProcessor(IMetaDataManager itemDataManager) {
        this.itemDataManager = itemDataManager;
    }

    @Override
    public void saveMetaData(Map<String, String> requestParams, MetaDataDto metaDataDto) {
        itemDataManager.saveMetaData(metaDataDto);
    }

    @Override
    public void updateMetaData(Map<String, String> requestParams, MetaDataDto metaDataDto) {
        String itemId = requestParams.get("itemId");
        if (itemId != null) {
            itemDataManager.updateMetaData(itemId, metaDataDto);
        }
    }

    @Override
    public void deleteMetaData(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        itemDataManager.deleteMetaData(itemId);
    }

    @Override
    public ResponseMetaDataDto getMetaData(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        return itemDataManager.getMetaData(itemId);
    }

    @Override
    public List<ResponseMetaDataDto> getMetaDataList(Map<String, String> requestParams) {
        return itemDataManager.getMetaDataList();
    }
}
