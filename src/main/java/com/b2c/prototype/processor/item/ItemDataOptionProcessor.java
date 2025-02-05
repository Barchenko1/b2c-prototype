package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.manager.item.IItemDataOptionManager;

import java.util.List;

public class ItemDataOptionProcessor implements IItemDataOptionProcessor {

    private final IItemDataOptionManager itemDataOptionManager;

    public ItemDataOptionProcessor(IItemDataOptionManager itemDataOptionManager) {
        this.itemDataOptionManager = itemDataOptionManager;
    }

    @Override
    public void saveUpdateItemDataOption(String itemId, List<ItemDataOptionDto> itemDataOptionDtoList) {
        itemDataOptionManager.saveUpdateItemDataOption(itemId, itemDataOptionDtoList);
    }

    @Override
    public void deleteItemDataOption(String articularId) {
        itemDataOptionManager.deleteItemDataOption(articularId);
    }

    @Override
    public ResponseItemDataOptionDto getResponseItemDataOptionDto(String articularId) {
        return itemDataOptionManager.getResponseItemDataOptionDto(articularId);
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList() {
        return itemDataOptionManager.getResponseItemDataOptionDtoList();
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered() {
        return itemDataOptionManager.getResponseItemDataOptionDtoFiltered();
    }

    @Override
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType) {
        return itemDataOptionManager.getResponseItemDataOptionDtoSorted(sortType);
    }
}
