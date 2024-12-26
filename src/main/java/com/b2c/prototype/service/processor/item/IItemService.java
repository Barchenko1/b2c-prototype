package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.ItemDto;
import com.b2c.prototype.modal.dto.update.ItemDtoUpdate;
import com.b2c.prototype.modal.entity.item.Item;

import java.util.List;
import java.util.Optional;

public interface IItemService {

    void saveUpdateItem(ItemDtoUpdate itemDtoUpdate);
    void deleteItem(OneFieldEntityDto oneFieldEntityDto);

    Item getItemByItemId(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByCategory(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByItemType(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByBrand(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByItemStatus(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByDateOfCreate(OneFieldEntityDto oneFieldEntityDto);
}
