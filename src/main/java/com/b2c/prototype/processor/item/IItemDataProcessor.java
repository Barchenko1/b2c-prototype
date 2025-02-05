package com.b2c.prototype.processor.item;

import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;

import java.util.List;

public interface IItemDataProcessor {
    void saveItemData(ItemDataDto itemDataDto);
    void updateItemData(String itemId, ItemDataDto itemDataDto);
    void deleteItemData(String itemId);

    ResponseItemDataDto getItemData(String itemId);
    List<ResponseItemDataDto> getItemDataList();
    List<ResponseItemDataDto> getItemDataListFiltered();
    List<ResponseItemDataDto> getItemDataListSorted(String sortType);
}
