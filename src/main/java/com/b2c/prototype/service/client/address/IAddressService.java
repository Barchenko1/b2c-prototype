package com.b2c.prototype.service.client.address;

import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.client.dto.update.RequestAddressDtoUpdate;

public interface IAddressService {
    void saveAddress(RequestAddressDto addressDto);
    void updateAppUserAddress(RequestAddressDtoUpdate requestAddressDtoUpdate);
    void updateDeliveryAddress(RequestAddressDtoUpdate requestAddressDtoUpdate);
    void deleteAppUserAddress(RequestOneFieldEntityDto requestOneFieldEntityDto);
    void deleteDeliveryAddress(RequestOneFieldEntityDto requestOneFieldEntityDto);
}
