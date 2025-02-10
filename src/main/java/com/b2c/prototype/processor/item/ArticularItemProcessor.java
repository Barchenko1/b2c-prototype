package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.manager.item.IArticularItemManager;

import java.util.List;

public class ArticularItemProcessor implements IArticularItemProcessor {

    private final IArticularItemManager itemDataOptionManager;

    public ArticularItemProcessor(IArticularItemManager itemDataOptionManager) {
        this.itemDataOptionManager = itemDataOptionManager;
    }

    @Override
    public void saveUpdateItemDataOption(String itemId, List<ArticularItemDto> articularItemDtoList) {
        itemDataOptionManager.saveUpdateItemDataOption(itemId, articularItemDtoList);
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
