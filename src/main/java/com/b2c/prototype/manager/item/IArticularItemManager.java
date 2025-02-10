package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.response.ResponseItemDataOptionDto;

import java.util.List;

public interface IArticularItemManager {
    void saveUpdateItemDataOption(String itemId, List<ArticularItemDto> articularItemDtoList);
    void deleteItemDataOption(String articularId);

    ResponseItemDataOptionDto getResponseItemDataOptionDto(String articularId);
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoList();
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoFiltered();
    List<ResponseItemDataOptionDto> getResponseItemDataOptionDtoSorted(String sortType);


}
