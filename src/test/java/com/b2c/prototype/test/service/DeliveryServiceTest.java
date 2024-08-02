package com.b2c.prototype.test.service;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.dao.delivery.IAddressDao;
import com.b2c.prototype.modal.client.dto.request.RequestAddressDto;
import com.b2c.prototype.modal.client.dto.request.RequestDeliveryDto;
import com.b2c.prototype.modal.client.entity.address.Address;
import com.b2c.prototype.modal.client.entity.delivery.Delivery;
import com.b2c.prototype.modal.client.entity.delivery.DeliveryType;
import com.b2c.prototype.service.client.delivery.base.DeliveryService;
import com.b2c.prototype.service.client.delivery.IDeliveryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

public class DeliveryServiceTest {

    @Mock
    private IDeliveryDao deliveryDao;

    @Mock
    private IAddressDao addressDao;

    @Mock
    private Map<String, DeliveryType> deliveryTypeMap;

    private IDeliveryService deliveryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deliveryService = new DeliveryService(deliveryDao, addressDao, deliveryTypeMap);
    }

    @Test
    public void testSaveDelivery() {
        // Mock behavior of deliveryAddressDao.saveEntity
        doNothing().when(addressDao).saveEntity(any(Address.class));

        // Mock behavior of deliveryTypeMap
        DeliveryType mockDeliveryType = new DeliveryType();
        when(deliveryTypeMap.get(any(String.class))).thenReturn(mockDeliveryType);

        // Create a request DTO
        RequestAddressDto requestAddressDto = new RequestAddressDto();
        requestAddressDto.setStreet("Test Street");
        requestAddressDto.setBuilding(123);
        requestAddressDto.setFlor(1);
        requestAddressDto.setApartmentNumber(101);
        RequestDeliveryDto requestDeliveryDto = new RequestDeliveryDto();
        requestDeliveryDto.setDeliveryAddressDto(requestAddressDto);
        requestDeliveryDto.setDeliveryType("TestType");

        // Test saveDelivery method
        deliveryService.saveDelivery(requestDeliveryDto);

        Address address = Address.builder()
                .flor(requestAddressDto.getFlor())
                .apartmentNumber(requestAddressDto.getApartmentNumber())
                .building(requestAddressDto.getBuilding())
                .street(requestAddressDto.getStreet())
                .build();
        // Verify that saveEntity was called with the correct DeliveryAddress
        verify(addressDao).saveEntity(address);

        DeliveryType deliveryType = DeliveryType.builder()
                .name(null)
                .build();

        Delivery delivery = Delivery.builder()
                .deliveryType(deliveryType)
                .address(address)
                .build();
        // Verify that saveEntity was called with the correct Delivery
        verify(deliveryDao).saveEntity(delivery);
    }

    @Test
    public void testDeleteDelivery() {
        // Mock delivery address and delivery type map
        Address address = Address.builder()
                .flor(1)
                .apartmentNumber(101)
                .building(123)
                .street("Test Street")
                .build();

        Map<String, DeliveryType> deliveryTypeMap = new HashMap<>();
        deliveryTypeMap.put("TestType", new DeliveryType());

        // Mock behavior of addressDao.saveEntity
        doNothing().when(addressDao).saveEntity(any(Address.class));

        // Mock behavior of addressDao.mutateEntityBySQLQueryWithParams
        RequestAddressDto requestAddressDto = new RequestAddressDto();
        requestAddressDto.setStreet("Test Street");
        requestAddressDto.setBuilding(123);
        requestAddressDto.setFlor(1);
        requestAddressDto.setApartmentNumber(101);
        RequestDeliveryDto requestDeliveryDto = new RequestDeliveryDto();
        requestDeliveryDto.setDeliveryAddressDto(requestAddressDto);

        deliveryService.deleteDelivery(requestDeliveryDto);

        // Verify that mutateEntityBySQLQueryWithParams was called with the correct parameters
        verify(deliveryDao).mutateEntityBySQLQueryWithParams(
                any(String.class),
                any(String.class),
                any(Integer.class),
                any(Integer.class),
                any(Integer.class)
        );
    }
}