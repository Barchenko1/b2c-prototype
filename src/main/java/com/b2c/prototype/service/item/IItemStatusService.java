package com.b2c.prototype.service.item;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IItemStatusService {
    void saveItemStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateItemStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteItemStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
