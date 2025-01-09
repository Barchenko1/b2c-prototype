package com.b2c.prototype.service.processor.address;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.searchfield.AddressSearchFieldEntityDto;

import java.util.List;

public interface IAddressService {
    void saveUpdateAppUserAddress(AddressSearchFieldEntityDto addressSearchFieldEntityDto);
    void saveUpdateDeliveryAddress(AddressSearchFieldEntityDto addressSearchFieldEntityDto);
    void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto);
    void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto);

    AddressDto getAddressByUserId(OneFieldEntityDto oneFieldEntityDto);
    AddressDto getAddressByOrderId(OneFieldEntityDto oneFieldEntityDto);

    List<AddressDto> getAddresses();
}
