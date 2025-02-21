package com.b2c.prototype.manager.address;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.searchfield.AddressSearchFieldEntityDto;

import java.util.List;

public interface IAddressManager {
    void saveUpdateAppUserAddress(AddressSearchFieldEntityDto addressSearchFieldEntityDto);
    void saveUpdateDeliveryAddress(AddressSearchFieldEntityDto addressSearchFieldEntityDto);
    void deleteAppUserAddress(OneFieldEntityDto oneFieldEntityDto);
    void deleteDeliveryAddress(OneFieldEntityDto oneFieldEntityDto);

    AddressDto getAddressByUserId(OneFieldEntityDto oneFieldEntityDto);
    AddressDto getAddressByOrderId(OneFieldEntityDto oneFieldEntityDto);

    List<AddressDto> getAddresses();
}
