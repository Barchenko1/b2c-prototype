package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.request.ItemDto;
import com.b2c.prototype.modal.dto.update.ItemDtoUpdate;
import com.b2c.prototype.modal.entity.item.Item;

import java.util.List;
import java.util.Optional;

public interface IItemService {

    void saveItem(ItemDto itemDto);
    void updateItem(ItemDtoUpdate requestItemDtoUpdate);
    void deleteItemByArticularId(String articularId);

    Optional<Item> getItem(String name);
    List<Item> getItemListByCategory(String categoryName);
    List<Item> getItemListByItemType(String itemTypeName);
    List<Item> getItemListByBrand(String brandName);
    List<Item> getItemListByItemStatus(String itemStatusName);
    List<Item> getItemListByDateOfCreate(String dateOfCreate);
}
