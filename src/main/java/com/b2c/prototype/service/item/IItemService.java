package com.b2c.prototype.service.item;

import com.b2c.prototype.modal.dto.request.RequestItemDto;
import com.b2c.prototype.modal.dto.update.RequestItemDtoUpdate;
import com.b2c.prototype.modal.entity.item.Item;

import java.util.List;
import java.util.Optional;

public interface IItemService {

    void saveItem(RequestItemDto requestItemDto);
    void updateItem(RequestItemDtoUpdate requestItemDtoUpdate);
    void deleteItemByArticularId(String articularId);

    Optional<Item> getItem(String name);
    List<Item> getItemListByCategory(String categoryName);
    List<Item> getItemListByItemType(String itemTypeName);
    List<Item> getItemListByBrand(String brandName);
    List<Item> getItemListByItemStatus(String itemStatusName);
    List<Item> getItemListByDateOfCreate(String dateOfCreate);
}
