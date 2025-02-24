package com.b2c.prototype.manager.item;

import com.b2c.prototype.modal.dto.payload.ItemDataOptionOneQuantityDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;

public interface IArticularItemQuantityManager {
    void increaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);
    void decreaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);
    void increaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);
    void decreaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);

    void increaseItemDataOptionQuantityCount(ArticularItemQuantityDto articularItemQuantityDto);
    void decreaseItemDataOptionQuantityCount(ArticularItemQuantityDto articularItemQuantityDto);

    void increaseItemDataOptionQuantityCountAndStore(ArticularItemQuantityDto articularItemQuantityDto);
    void decreaseItemDataOptionQuantityCountAndStore(ArticularItemQuantityDto articularItemQuantityDto);
}
