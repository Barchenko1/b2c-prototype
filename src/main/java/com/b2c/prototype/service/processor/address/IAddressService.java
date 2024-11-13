package com.b2c.prototype.service.processor.address;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.update.AddressDtoUpdate;

public interface IAddressService {
    void saveAddress(AddressDto addressDto);
    void updateAppUserAddress(AddressDtoUpdate requestAddressDtoUpdate);
    void updateDeliveryAddress(AddressDtoUpdate requestAddressDtoUpdate);
    void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto);
    void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto);

    AddressDto getAddressByEmail(String email);
    AddressDto getAddressByUsername(String username);
    AddressDto getAddressByOrderId(String orderId);
}
