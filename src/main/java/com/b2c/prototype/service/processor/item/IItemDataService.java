package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDataDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataDto;
import com.b2c.prototype.modal.dto.update.ItemDataDtoUpdate;

import java.util.List;

public interface IItemDataService {
    void saveItemData(ItemDataDto itemDataDto);
    void updateItemData(ItemDataDtoUpdate itemDataDtoUpdate);
    void deleteItemData(OneFieldEntityDto oneFieldEntityDto);

    ResponseItemDataDto getItemData(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseItemDataDto> getItemDataList();
    List<ResponseItemDataDto> getItemDataListFiltered();
    List<ResponseItemDataDto> getItemDataListSorted(String sortType);
}
