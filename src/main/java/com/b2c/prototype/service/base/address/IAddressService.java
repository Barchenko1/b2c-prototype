package com.b2c.prototype.service.base.address;

import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.dto.update.RequestAddressDtoUpdate;

public interface IAddressService {
    void saveAddress(RequestAddressDto addressDto);
    void updateAppUserAddress(RequestAddressDtoUpdate requestAddressDtoUpdate);
    void updateDeliveryAddress(RequestAddressDtoUpdate requestAddressDtoUpdate);
    void deleteAppUserAddress(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void deleteDeliveryAddress(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
