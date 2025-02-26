package com.b2c.prototype.manager.delivery.base;

import com.b2c.prototype.dao.delivery.IDeliveryDao;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.DeliveryDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeliveryManagerTest {

    @Mock
    private IDeliveryDao deliveryDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private DeliveryManager deliveryManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateDelivery() {
        DeliveryDto deliveryDto = getDeliveryDto();
        String orderId = "searchField";
        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Delivery existingDelivery = getDelivery();
        Delivery newDelivery = getDelivery();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;


        when(orderItemDataOption.getDelivery()).thenReturn(existingDelivery);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(Delivery.class, deliveryDto))
                .thenReturn(newDelivery);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemDataOption);
            return null;
        }).when(deliveryDao).executeConsumer(any(Consumer.class));

        deliveryManager.saveUpdateDelivery(orderId, deliveryDto);

        verify(deliveryDao).executeConsumer(any(Consumer.class));
        assertEquals(existingDelivery.getId(), newDelivery.getId());
    }

    @Test
    void testSaveUpdateDeliveryNull() {
        DeliveryDto deliveryDto = getDeliveryDto();
        String orderId = "searchField";

        OrderArticularItem orderItemDataOption = mock(OrderArticularItem.class);
        Delivery existingDelivery = null;
        Delivery newDelivery = getDelivery();
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        when(orderItemDataOption.getDelivery()).thenReturn(existingDelivery);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderArticularItem.class, parameterSupplier))
                .thenReturn(orderItemDataOption);
        when(transformationFunctionService.getEntity(Delivery.class, deliveryDto))
                .thenReturn(newDelivery);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(orderItemDataOption);
            return null;
        }).when(deliveryDao).executeConsumer(any(Consumer.class));

        deliveryManager.saveUpdateDelivery(orderId, deliveryDto);

        verify(deliveryDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteDelivery() {
        String orderId = "123";
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        Delivery delivery = getDelivery();
        Supplier<Delivery> deliverySupplier = () -> delivery;

        Function<OrderArticularItem, Delivery> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, Delivery.class))
                .thenReturn(function);
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(supplierService.entityFieldSupplier(
                OrderArticularItem.class,
                parameterSupplier,
                function
        )).thenReturn(deliverySupplier);

        deliveryManager.deleteDelivery(orderId);

        verify(deliveryDao).deleteEntity(deliverySupplier);
    }

    @Test
    void testGetDelivery() {
        String orderId = "123";
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        DeliveryDto deliveryDto =  getDeliveryDto();
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        Function<OrderArticularItem, DeliveryDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItem.class, DeliveryDto.class))
                .thenReturn(function);
        when(queryService.getEntityDto(OrderArticularItem.class, parameterSupplier, function))
                .thenReturn(deliveryDto);

        DeliveryDto result = deliveryManager.getDelivery(orderId);

        assertNotNull(result);
    }

    @Test
    void testGetDeliveries() {
        Delivery delivery = getDelivery();
        DeliveryDto deliveryDto =  getDeliveryDto();
        Function<Delivery, DeliveryDto> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(Delivery.class, DeliveryDto.class))
                .thenReturn(function);
        when(function.apply(delivery)).thenReturn(deliveryDto);
        when(deliveryDao.getEntityList())
                .thenReturn(List.of(delivery));
        List<DeliveryDto> resultList = deliveryManager.getDeliveries();

        assertNotNull(resultList);
        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            assertEquals(deliveryDto, result);
        });

    }

    private AddressDto getAddressDto() {
        return AddressDto.builder()
                .country("USA")
                .city("city")
                .street("street")
                .street2("street2")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91000")
                .build();
    }

    private DeliveryDto getDeliveryDto() {
        return DeliveryDto.builder()
                .deliveryAddress(getAddressDto())
                .deliveryType("STH")
                .build();
    }

    private DeliveryType getDeliveryType() {
        return DeliveryType.builder()
                .id(1L)
                .value("STH")
                .build();
    }

    private Country getCountry() {
        return Country.builder()
                .id(1L)
                .value("USA")
                .build();
    }

    private Address getAddress() {
        return Address.builder()
                .country(getCountry())
                .city("city")
                .street("street")
                .street2("street2")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91000")
                .build();
    }

    private Delivery getDelivery() {
        return Delivery.builder()
                .address(getAddress())
                .deliveryType(getDeliveryType())
                .build();
    }
}
