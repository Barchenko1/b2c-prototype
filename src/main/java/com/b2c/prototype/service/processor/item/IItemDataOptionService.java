package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;
import com.b2c.prototype.modal.dto.update.ItemDataOptionDtoUpdate;
import com.b2c.prototype.modal.entity.item.ItemDataOption;

import java.util.List;

public interface IItemDataOptionService {
    void saveItemDataOption(ItemDataOptionDto itemDataOptionDto);
    void updateItemDataOption(ItemDataOptionDtoUpdate itemDataOptionDtoUpdate);
    void deleteItemDataOption(OneFieldEntityDto oneFieldEntityDto);

    public ResponseItemDataOptionDto getResponseItemDataOptionDto(OneFieldEntityDto oneFieldEntityDto);
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList();
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered();
    public List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType);


}
