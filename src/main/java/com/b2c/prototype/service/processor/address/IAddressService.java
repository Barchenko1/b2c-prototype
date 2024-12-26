package com.b2c.prototype.service.processor.address;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.update.AddressSearchFieldDto;

import java.util.List;

public interface IAddressService {
    void saveUpdateAppUserAddress(AddressSearchFieldDto addressSearchFieldDto);
    void saveUpdateDeliveryAddress(AddressSearchFieldDto addressSearchFieldDto);
    void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto);
    void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto);

    AddressDto getAddressByUserId(OneFieldEntityDto oneFieldEntityDto);
    AddressDto getAddressByOrderId(OneFieldEntityDto oneFieldEntityDto);

    List<AddressDto> getAddresses();
}
