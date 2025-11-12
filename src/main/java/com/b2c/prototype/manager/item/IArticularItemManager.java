package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;

import java.util.List;

public interface IArticularItemManager {
    void saveUpdateArticularItem(String itemId, List<ArticularItemDto> articularItemDtoList);
    void deleteArticularItem(String articularId);

    ArticularItemDto getArticularItemDto(String articularId);
    List<ArticularItemDto> getArticularItemDtoList();
    List<ArticularItemDto> getArticularItemDtoFiltered();
    List<ArticularItemDto> getArticularItemDtoSorted(String sortType);


}
