package com.b2c.prototype.service.client.delivery.base;

import com.b2c.prototype.dao.delivery.IAddressDao;
import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.client.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.client.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.client.dto.update.RequestDeliveryDtoUpdate;
import com.b2c.prototype.modal.client.entity.address.Address;
import com.b2c.prototype.modal.client.entity.delivery.Delivery;
import com.b2c.prototype.modal.client.entity.delivery.DeliveryType;
import com.b2c.prototype.service.client.delivery.IDeliveryService;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

import static com.b2c.prototype.util.Query.DELETE_DELIVERY_BY_DELIVERY_ADDRESS;

@Slf4j
public class DeliveryService implements IDeliveryService {
    private final IDeliveryDao deliveryDao;
    private final IAddressDao addressDao;
    private final Map<String, DeliveryType> deliveryTypeMap;

    public DeliveryService(IDeliveryDao deliveryDao, IAddressDao addressDao, Map<String, DeliveryType> deliveryTypeMap) {
        this.deliveryDao = deliveryDao;
        this.addressDao = addressDao;
        this.deliveryTypeMap = deliveryTypeMap;
    }

    @Override
    public void saveDelivery(RequestDeliveryDto requestDeliveryDto) {
        RequestAddressDto requestAddressDto =
                requestDeliveryDto.getDeliveryAddressDto();
        Address address = Address.builder()
                .flor(requestAddressDto.getFlor())
                .apartmentNumber(requestAddressDto.getApartmentNumber())
                .building(requestAddressDto.getBuilding())
                .street(requestAddressDto.getStreet())
                .build();
        addressDao.saveEntity(address);

        Delivery delivery = Delivery.builder()
                .deliveryType(deliveryTypeMap.get(requestDeliveryDto.getDeliveryType()))
                .address(address)
                .build();
        deliveryDao.saveEntity(delivery);
    }

    @Override
    public void updateDeliveryAddress(RequestDeliveryDtoUpdate requestDeliveryDtoUpdate) {

    }

    @Override
    public void updateDeliveryType(RequestDeliveryDtoUpdate requestDeliveryDtoUpdate) {

    }

    @Override
    public void deleteDelivery(RequestDeliveryDto requestDeliveryDto) {
        RequestAddressDto requestAddressDto =
                requestDeliveryDto.getDeliveryAddressDto();
        deliveryDao.mutateEntityBySQLQueryWithParams(
                DELETE_DELIVERY_BY_DELIVERY_ADDRESS,
                requestAddressDto.getStreet(),
                requestAddressDto.getBuilding(),
                requestAddressDto.getFlor(),
                requestAddressDto.getApartmentNumber()
        );
    }
}
