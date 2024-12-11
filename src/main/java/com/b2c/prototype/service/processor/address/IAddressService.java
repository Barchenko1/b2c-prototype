package com.b2c.prototype.service.processor.address;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.update.AddressDtoUpdate;

import java.util.List;

public interface IAddressService {
    void saveAddress(AddressDto addressDto);
    void updateAppUserAddress(AddressDtoUpdate requestAddressDtoUpdate);
    void updateDeliveryAddress(AddressDtoUpdate requestAddressDtoUpdate);
    void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto);
    void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto);

    AddressDto getAddressByEmail(OneFieldEntityDto oneFieldEntityDto);
    AddressDto getAddressByUsername(OneFieldEntityDto oneFieldEntityDto);
    AddressDto getAddressByOrderId(OneFieldEntityDto oneFieldEntityDto);

    List<AddressDto> getAddresses();
}
