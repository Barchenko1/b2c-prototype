package com.b2c.prototype.service.processor.item;

import com.b2c.prototype.modal.dto.request.ItemDataQuantityDto;

public interface IItemQuantityService {
    void increaseOneItemDataCount(ItemDataQuantityDto itemDataQuantityDto);
    void decreaseOneItemDataCount(ItemDataQuantityDto itemDataQuantityDto);

    void increaseOneItemDataCountAndStore(ItemDataQuantityDto itemDataQuantityDto);
    void decreaseOneItemDataCountAndStore(ItemDataQuantityDto itemDataQuantityDto);

    void updateItemQuantity(ItemDataQuantityDto itemDataQuantityCountDto);
    void updateItemQuantityAndStore(ItemDataQuantityDto itemDataQuantityCountDto);

}
