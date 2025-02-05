package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.ItemDataSearchFieldEntityDto;

import java.util.List;

public interface IItemDataManager {
    void saveItemData(ItemDataDto itemDataDto);
    void updateItemData(String itemId, ItemDataDto itemDataDto);
    void deleteItemData(String itemId);

    ResponseItemDataDto getItemData(String itemId);
    List<ResponseItemDataDto> getItemDataList();
    List<ResponseItemDataDto> getItemDataListFiltered();
    List<ResponseItemDataDto> getItemDataListSorted(String sortType);
}
