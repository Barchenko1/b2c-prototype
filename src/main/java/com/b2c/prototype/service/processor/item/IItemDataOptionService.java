package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.ItemDataOptionArraySearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;

import java.util.List;

public interface IItemDataOptionService {
    void saveUpdateItemDataOption(ItemDataOptionArraySearchFieldEntityDto itemDataOptionArraySearchFieldEntityDto);
    void deleteItemDataOption(OneFieldEntityDto oneFieldEntityDto);

    public ResponseItemDataOptionDto getResponseItemDataOptionDto(OneFieldEntityDto oneFieldEntityDto);
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList();
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered();
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType);


}
