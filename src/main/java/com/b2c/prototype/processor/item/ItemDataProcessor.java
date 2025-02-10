package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.manager.item.IItemDataManager;

import java.util.List;
import java.util.Map;

public class ItemDataProcessor implements IItemDataProcessor {

    private final IItemDataManager itemDataManager;

    public ItemDataProcessor(IItemDataManager itemDataManager) {
        this.itemDataManager = itemDataManager;
    }

    @Override
    public void saveItemData(Map<String, String> requestParams, ItemDataDto itemDataDto) {
        itemDataManager.saveItemData(itemDataDto);
    }

    @Override
    public void updateItemData(Map<String, String> requestParams, ItemDataDto itemDataDto) {
        String itemId = requestParams.get("itemId");
        if (itemId != null) {
            itemDataManager.updateItemData(itemId, itemDataDto);
        }
    }

    @Override
    public void deleteItemData(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        itemDataManager.deleteItemData(itemId);
    }

    @Override
    public ResponseItemDataDto getItemData(Map<String, String> requestParams) {
        String itemId = requestParams.get("itemId");
        return itemDataManager.getItemData(itemId);
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList(Map<String, String> requestParams) {
        return itemDataManager.getItemDataList();
    }
}
