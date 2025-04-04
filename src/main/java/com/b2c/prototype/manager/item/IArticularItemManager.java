package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.item.ResponseArticularItemDto;

import java.util.List;

public interface IArticularItemManager {
    void saveUpdateArticularItem(String itemId, List<ArticularItemDto> articularItemDtoList);
    void deleteArticularItem(String articularId);

    ResponseArticularItemDto getResponseArticularItemDto(String articularId);
    List<ResponseArticularItemDto> getResponseArticularItemDtoList();
    List<ResponseArticularItemDto> getResponseArticularItemDtoFiltered();
    List<ResponseArticularItemDto> getResponseArticularItemDtoSorted(String sortType);


}
