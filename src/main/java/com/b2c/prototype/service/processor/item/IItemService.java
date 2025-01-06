package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.searchfield.ItemSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.item.Item;

import java.util.List;

public interface IItemService {

    void saveUpdateItem(ItemSearchFieldEntityDto itemSearchFieldEntityDto);
    void deleteItem(OneFieldEntityDto oneFieldEntityDto);

    Item getItemByItemId(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByCategory(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByItemType(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByBrand(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByItemStatus(OneFieldEntityDto oneFieldEntityDto);
    List<Item> getItemListByDateOfCreate(OneFieldEntityDto oneFieldEntityDto);
}
