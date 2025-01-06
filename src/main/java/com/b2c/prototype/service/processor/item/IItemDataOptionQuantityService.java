package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.request.ItemDataOptionOneQuantityDto;
import com.b2c.prototype.modal.dto.request.ItemDataOptionQuantityDto;

public interface IItemDataOptionQuantityService {
    void increaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);
    void decreaseOneItemDataOptionQuantityCount(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);
    void increaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);
    void decreaseOneItemDataOptionQuantityCountAndStore(ItemDataOptionOneQuantityDto itemDataOptionOneQuantityDto);

    void increaseItemDataOptionQuantityCount(ItemDataOptionQuantityDto itemDataOptionQuantityDto);
    void decreaseItemDataOptionQuantityCount(ItemDataOptionQuantityDto itemDataOptionQuantityDto);

    void increaseItemDataOptionQuantityCountAndStore(ItemDataOptionQuantityDto itemDataOptionQuantityDto);
    void decreaseItemDataOptionQuantityCountAndStore(ItemDataOptionQuantityDto itemDataOptionQuantityDto);
}
