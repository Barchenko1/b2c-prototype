package com.b2c.prototype.service.base.address;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.dto.update.RequestAddressDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AddressService extends AbstractSingleEntityService implements IAddressService {

    private final IAddressDao addressDao;

    public AddressService(IAddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.addressDao;
    }

    @Override
    public void saveAddress(RequestAddressDto addressDto) {
        Address address = Address.builder()
//                .country(addressDto.getCountry())
                .apartmentNumber(addressDto.getApartmentNumber())
                .flor(addressDto.getFlor())
                .street(addressDto.getStreet())
                .build();

        super.saveEntity(address);
    }

    @Override
    public void updateAppUserAddress(RequestAddressDtoUpdate requestAddressDtoUpdate) {
        RequestAddressDto newRequestAddressDto = requestAddressDtoUpdate.getNewEntityDto();
        Address newAddress = buildAddress(newRequestAddressDto);

        Parameter parameter = parameterFactory.createStringParameter("username",
                requestAddressDtoUpdate.getSearchField());
        super.updateEntity(newAddress, parameter);
    }

    @Override
    public void updateDeliveryAddress(RequestAddressDtoUpdate requestAddressDtoUpdate) {
        RequestAddressDto newRequestAddressDto = requestAddressDtoUpdate.getNewEntityDto();
        Address newAddress = buildAddress(newRequestAddressDto);

        Parameter parameter = parameterFactory.createStringParameter("order_id",
                requestAddressDtoUpdate.getSearchField());
        super.updateEntity(newAddress, parameter);
    }

    @Override
    public void deleteAppUserAddress(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter = parameterFactory.createStringParameter("username",
                requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);

    }

    @Override
    public void deleteDeliveryAddress(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter = parameterFactory.createStringParameter("order_id",
                requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
    }

    private Address buildAddress(RequestAddressDto requestAddressDto) {
        return Address.builder()
//                .country(requestAddressDto.getCountry())
                .street(requestAddressDto.getStreet())
                .buildingNumber(requestAddressDto.getBuildingNumber())
                .flor(requestAddressDto.getFlor())
                .apartmentNumber(requestAddressDto.getApartmentNumber())
                .build();
    }
}
