package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.item.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseItemDataDto;
import com.b2c.prototype.manager.item.IItemDataManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class MetaDataProcessor implements IMetaDataProcessor {

    private final IItemDataManager itemDataManager;

    public MetaDataProcessor(IItemDataManager itemDataManager) {
        this.itemDataManager = itemDataManager;
    }

    @Override
    public void saveMetaData(Map<String, String> requestParams, ItemDataDto itemDataDto) {
        itemDataManager.saveItemData(itemDataDto);
    }

    @Override
    public void updateMetaData(Map<String, String> requestParams, ItemDataDto itemDataDto) {
        String itemId = requestParams.get("itemId");
        if (itemId != null) {
            itemDataManager.updateItemData(itemId, itemDataDto);
        }
    }

    @Override
    public void deleteMetaData(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        itemDataManager.deleteItemData(itemId);
    }

    @Override
    public ResponseItemDataDto getMetaData(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        return itemDataManager.getItemData(itemId);
    }

    @Override
    public List<ResponseItemDataDto> getMetaDataList(Map<String, String> requestParams) {
        return itemDataManager.getItemDataList();
    }
}
