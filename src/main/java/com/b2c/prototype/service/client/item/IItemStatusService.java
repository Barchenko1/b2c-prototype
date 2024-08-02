package com.b2c.prototype.service.client.item;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;

public interface IItemStatusService {
    void saveItemStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void updateItemStatus(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate);
    void deleteItemStatus(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
