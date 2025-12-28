//package com.b2c.prototype.manager.delivery.base;
//
//import com.b2c.prototype.dao.delivery.IDeliveryDao;
//import com.b2c.prototype.transform.transform.payload.order.AddressDto;
//import com.b2c.prototype.transform.transform.payload.order.single.DeliveryDto;
//import com.b2c.prototype.transform.entity.address.Address;
//import com.b2c.prototype.transform.entity.address.Country;
//import com.b2c.prototype.transform.entity.delivery.Delivery;
//import com.b2c.prototype.transform.entity.delivery.DeliveryType;
//import com.b2c.prototype.transform.entity.order.DeliveryArticularItemQuantity;
//import com.b2c.prototype.function.transform.ITransformationFunctionService;
//import com.tm.core.finder.parameter.Parameter;
//import org.hibernate.Session;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.List;
//import java.util.function.Consumer;
//import java.util.function.Function;
//import java.util.function.Supplier;
//
//import static com.b2c.prototype.util.Constant.ORDER_ID;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.mockito.Mockito.any;
//import static org.mockito.Mockito.doAnswer;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class DeliveryManagerTest {
//
//    @Mock
//    private IDeliveryDao deliveryDao;
//    @Mock
//    private ISearchService queryService;
//    @Mock
//    private ITransformationFunctionService transformationFunctionService;
//    @Mock
//    private ISupplierService supplierService;
//    @InjectMocks
//    private DeliveryManager deliveryManager;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    void testSaveUpdateDelivery() {
//        DeliveryDto deliveryDto = getDeliveryDto();
//        String orderId = "searchField";
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        Delivery existingDelivery = getDelivery();
//        Delivery newDelivery = getDelivery();
//        
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//
//        when(orderItemDataOption.getDelivery()).thenReturn(existingDelivery);
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
//        when(transformationFunctionService.getEntity(Delivery.class, deliveryDto))
//                .thenReturn(newDelivery);
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(orderItemDataOption);
//            return null;
//        }).when(deliveryDao).executeConsumer(any(Consumer.class));
//
//        deliveryManager.saveUpdateDelivery(orderId, deliveryDto);
//
//        verify(deliveryDao).executeConsumer(any(Consumer.class));
//        assertEquals(existingDelivery.getId(), newDelivery.getId());
//    }
//
//    @Test
//    void testSaveUpdateDeliveryNull() {
//        DeliveryDto deliveryDto = getDeliveryDto();
//        String orderId = "searchField";
//
//        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
//        Delivery existingDelivery = null;
//        Delivery newDelivery = getDelivery();
//        
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        when(orderItemDataOption.getDelivery()).thenReturn(existingDelivery);
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
////        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
////                .thenReturn(orderItemDataOption);
//        when(transformationFunctionService.getEntity(Delivery.class, deliveryDto))
//                .thenReturn(newDelivery);
//        doAnswer(invocation -> {
//            Consumer<Session> consumer = invocation.getArgument(0);
//            Session session = mock(Session.class);
//            consumer.accept(session);
//            verify(session).merge(orderItemDataOption);
//            return null;
//        }).when(deliveryDao).executeConsumer(any(Consumer.class));
//
//        deliveryManager.saveUpdateDelivery(orderId, deliveryDto);
//
//        verify(deliveryDao).executeConsumer(any(Consumer.class));
//    }
//
//    @Test
//    void testDeleteDelivery() {
//        String orderId = "123";
//        
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//        Delivery delivery = getDelivery();
//        Supplier<Delivery> deliverySupplier = () -> delivery;
//
//        Function<DeliveryArticularItemQuantity, Delivery> function = mock(Function.class);
//        when(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, Delivery.class))
//                .thenReturn(function);
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
////        when(supplierService.entityFieldSupplier(
////                DeliveryArticularItemQuantity.class,
////                parameterSupplier,
////                function
////        )).thenReturn(deliverySupplier);
//
//        deliveryManager.deleteDelivery(orderId);
//
//        verify(deliveryDao).deleteEntity(deliverySupplier);
//    }
//
//    @Test
//    void testGetDelivery() {
//        String orderId = "123";
//        
//        Supplier<Parameter> parameterSupplier = () -> parameter;
//
//        DeliveryDto deliveryDto =  getDeliveryDto();
//        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
//                .thenReturn(parameterSupplier);
//        Function<DeliveryArticularItemQuantity, DeliveryDto> function = mock(Function.class);
//        when(transformationFunctionService.getTransformationFunction(DeliveryArticularItemQuantity.class, DeliveryDto.class))
//                .thenReturn(function);
////        when(queryService.getEntityDto(DeliveryArticularItemQuantity.class, parameterSupplier, function))
////                .thenReturn(deliveryDto);
//
//        DeliveryDto result = deliveryManager.getDelivery(orderId);
//
//        assertNotNull(result);
//    }
//
//    @Test
//    void testGetDeliveries() {
//        Delivery delivery = getDelivery();
//        DeliveryDto deliveryDto =  getDeliveryDto();
//        Function<Delivery, DeliveryDto> function = mock(Function.class);
//        when(transformationFunctionService.getTransformationFunction(Delivery.class, DeliveryDto.class))
//                .thenReturn(function);
//        when(function.apply(delivery)).thenReturn(deliveryDto);
////        when(deliveryDao.getEntityList()).thenReturn(List.of(delivery));
//        List<DeliveryDto> resultList = deliveryManager.getDeliveries();
//
//        assertNotNull(resultList);
//        assertEquals(1, resultList.size());
//        resultList.forEach(result -> {
//            assertEquals(deliveryDto, result);
//        });
//
//    }
//
//    private AddressDto getAddressDto() {
//        return AddressDto.builder()
//                .country("USA")
//                .city("city")
//                .street("street")
//                .buildingNumber("1")
//                .florNumber(9)
//                .apartmentNumber(101)
//                .zipCode("91000")
//                .build();
//    }
//
//    private DeliveryDto getDeliveryDto() {
//        return DeliveryDto.builder()
//                .deliveryAddress(getAddressDto())
//                .deliveryType("STH")
//                .build();
//    }
//
//    private DeliveryType getDeliveryType() {
//        return DeliveryType.builder()
//                .id(1L)
//                .value("STH")
//                .build();
//    }
//
//    private Country getCountry() {
//        return Country.builder()
//                .id(1L)
//                .value("USA")
//                .build();
//    }
//
//    private Address getAddress() {
//        return Address.builder()
//                .country(getCountry())
//                .city("city")
//                .street("street")
//                .buildingNumber("1")
//                .florNumber(9)
//                .apartmentNumber(101)
//                .zipCode("91000")
//                .build();
//    }
//
//    private Delivery getDelivery() {
//        return Delivery.builder()
//                .address(getAddress())
//                .deliveryType(getDeliveryType())
//                .build();
//    }
//}
