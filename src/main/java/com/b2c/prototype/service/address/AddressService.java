package com.b2c.prototype.service.address;

import com.b2c.prototype.dao.delivery.IAddressDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.dto.update.RequestAddressDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.DELETE_ADDRESS_BY_ORDER_ID;
import static com.b2c.prototype.util.Query.DELETE_ADDRESS_BY_USERNAME;
import static com.b2c.prototype.util.Query.UPDATE_ADDRESS_BY_ORDER_ID;
import static com.b2c.prototype.util.Query.UPDATE_ADDRESS_BY_USERNAME;

@Slf4j
public class AddressService implements IAddressService {

    private final IAddressDao addressDao;

    public AddressService(IAddressDao addressDao1) {
        this.addressDao = addressDao1;
    }

    @Override
    public void saveAddress(RequestAddressDto addressDto) {
        Address address = Address.builder()
                .country(addressDto.getCountry())
                .apartmentNumber(addressDto.getApartmentNumber())
                .flor(addressDto.getFlor())
                .street(addressDto.getStreet())
                .build();

        addressDao.saveEntity(address);
    }

    @Override
    public void updateAppUserAddress(RequestAddressDtoUpdate requestAddressDtoUpdate) {
        RequestAddressDto newRequestAddressDto = requestAddressDtoUpdate.getNewEntityDto();
        Address newAddress = buildAddress(newRequestAddressDto);

        addressDao.mutateEntityBySQLQueryWithParams(UPDATE_ADDRESS_BY_USERNAME,
                newAddress.getCountry(),
                newAddress.getStreet(),
                newAddress.getBuildingNumber(),
                newAddress.getFlor(),
                newAddress.getApartmentNumber(),
                requestAddressDtoUpdate.getSearchField());
    }

    @Override
    public void updateDeliveryAddress(RequestAddressDtoUpdate requestAddressDtoUpdate) {
        RequestAddressDto newRequestAddressDto = requestAddressDtoUpdate.getNewEntityDto();
        Address newAddress = buildAddress(newRequestAddressDto);

        addressDao.mutateEntityBySQLQueryWithParams(UPDATE_ADDRESS_BY_ORDER_ID,
                newAddress.getCountry(),
                newAddress.getStreet(),
                newAddress.getBuildingNumber(),
                newAddress.getFlor(),
                newAddress.getApartmentNumber(),
                requestAddressDtoUpdate.getSearchField());
    }

    @Override
    public void deleteAppUserAddress(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        addressDao.mutateEntityBySQLQueryWithParams(DELETE_ADDRESS_BY_USERNAME,
                requestOneFieldEntityDto.getRequestValue());

    }

    @Override
    public void deleteDeliveryAddress(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        addressDao.mutateEntityBySQLQueryWithParams(DELETE_ADDRESS_BY_ORDER_ID,
                requestOneFieldEntityDto.getRequestValue());
    }

    private Address buildAddress(RequestAddressDto requestAddressDto) {
        return Address.builder()
                .country(requestAddressDto.getCountry())
                .street(requestAddressDto.getStreet())
                .buildingNumber(requestAddressDto.getBuildingNumber())
                .flor(requestAddressDto.getFlor())
                .apartmentNumber(requestAddressDto.getApartmentNumber())
                .build();
    }
}
