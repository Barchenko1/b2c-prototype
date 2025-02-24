package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.ItemDto;
import com.b2c.prototype.modal.entity.item.Item;

public interface IItemManager {

    void saveUpdateItem(String articularId, ItemDto itemDto);
    void deleteItem(String articularId);

    Item getItemByItemId(String articularId);
}
