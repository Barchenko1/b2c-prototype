package com.b2c.prototype.service.client.item;

import com.b2c.prototype.modal.client.dto.request.RequestItemDto;
import com.b2c.prototype.modal.client.dto.update.RequestItemDtoUpdate;
import com.b2c.prototype.modal.client.entity.item.Item;

import java.util.List;
import java.util.Optional;

public interface IItemService {

    void saveItem(RequestItemDto requestItemDto);
    void updateItem(RequestItemDtoUpdate requestItemDtoUpdate);
    void deleteItem(RequestItemDto requestItemDto);

    Optional<Item> getItem(String name);
    List<Item> getItemListByCategory(String categoryName);
    List<Item> getItemListByItemType(String itemTypeName);
    List<Item> getItemListByBrand(String brandName);
    List<Item> getItemListByItemStatus(String itemStatusName);
    List<Item> getItemListByDateOfCreate(String dateOfCreate);
}
