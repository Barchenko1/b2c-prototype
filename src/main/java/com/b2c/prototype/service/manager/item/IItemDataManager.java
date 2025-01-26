package com.b2c.prototype.service.manager.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.searchfield.ItemDataSearchFieldEntityDto;

import java.util.List;

public interface IItemDataManager {
    void saveItemData(ItemDataDto itemDataDto);
    void updateItemData(ItemDataSearchFieldEntityDto itemDataSearchFieldEntityDto);
    void deleteItemData(OneFieldEntityDto oneFieldEntityDto);

    ResponseItemDataDto getItemData(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseItemDataDto> getItemDataList();
    List<ResponseItemDataDto> getItemDataListFiltered();
    List<ResponseItemDataDto> getItemDataListSorted(String sortType);
}
