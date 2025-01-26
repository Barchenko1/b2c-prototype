package com.b2c.prototype.service.manager.item;

import com.b2c.prototype.modal.dto.searchfield.ItemDataOptionArraySearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;

import java.util.List;

public interface IItemDataOptionManager {
    void saveUpdateItemDataOption(ItemDataOptionArraySearchFieldEntityDto itemDataOptionArraySearchFieldEntityDto);
    void deleteItemDataOption(String articularId);

    ResponseItemDataOptionDto getResponseItemDataOptionDto(String articularId);
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList();
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered();
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType);


}
