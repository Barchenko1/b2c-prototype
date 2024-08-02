package com.b2c.prototype.service.client.item;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IItemTypeService {
    void saveItemType(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateItemType(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteItemType(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
