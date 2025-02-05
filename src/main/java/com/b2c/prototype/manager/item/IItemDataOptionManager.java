package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;

import java.util.List;

public interface IItemDataOptionManager {
    void saveUpdateItemDataOption(String itemId, List<ItemDataOptionDto> itemDataOptionDtoList);
    void deleteItemDataOption(String articularId);

    ResponseItemDataOptionDto getResponseItemDataOptionDto(String articularId);
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList();
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered();
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType);


}
