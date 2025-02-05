package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.manager.item.IItemDataManager;

import java.util.List;

public class ItemDataProcessor implements IItemDataProcessor {

    private final IItemDataManager itemDataManager;

    public ItemDataProcessor(IItemDataManager itemDataManager) {
        this.itemDataManager = itemDataManager;
    }

    @Override
    public void saveItemData(ItemDataDto itemDataDto) {
        itemDataManager.saveItemData(itemDataDto);
    }

    @Override
    public void updateItemData(String itemId, ItemDataDto itemDataDto) {
        itemDataManager.updateItemData(itemId, itemDataDto);
    }

    @Override
    public void deleteItemData(String itemId) {
        itemDataManager.deleteItemData(itemId);
    }

    @Override
    public ResponseItemDataDto getItemData(String itemId) {
        return itemDataManager.getItemData(itemId);
    }

    @Override
    public List<ResponseItemDataDto> getItemDataList() {
        return itemDataManager.getItemDataList();
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListFiltered() {
        return itemDataManager.getItemDataListFiltered();
    }

    @Override
    public List<ResponseItemDataDto> getItemDataListSorted(String sortType) {
        return itemDataManager.getItemDataListSorted(sortType);
    }
}
