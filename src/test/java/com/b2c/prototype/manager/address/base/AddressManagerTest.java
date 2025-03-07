package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderArticularItemQuantity;
import com.b2c.prototype.modal.entity.user.UserDetails;
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
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressManagerTest {

    @Mock
    private IAddressDao addressDao;
    @Mock
    private ISearchService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private AddressManager addressManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateAppUserAddress() {
        String userId = "123";
        AddressDto addressDto = getAddressDto();
        UserDetails userDetails = mock(UserDetails.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
//        when(queryService.getEntity(UserDetails.class, parameterSupplier))
//                .thenReturn(userDetails);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        }).when(addressDao).executeConsumer(any(Consumer.class));

        addressManager.saveUpdateAppUserAddress(userId, addressDto);

        verify(addressDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateDeliveryAddress() {
        String orderId = "123";
        AddressDto addressDto = getAddressDto();
        OrderArticularItemQuantity orderItemDataOption = mock(OrderArticularItemQuantity.class);
        Delivery delivery = mock(Delivery.class);
        when(orderItemDataOption.getDelivery()).thenReturn(delivery);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
//        when(queryService.getEntity(OrderArticularItemQuantity.class, parameterSupplier))
//                .thenReturn(orderItemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(delivery);
            return null;
        }).when(addressDao).executeConsumer(any(Consumer.class));

        addressManager.saveUpdateDeliveryAddress(orderId, addressDto);

        verify(addressDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteAppUserAddress() {
        String userId = "userId";
        UserDetails userDetails = mock(UserDetails.class);
        Address address = mock(Address.class);

//        when(userDetails.getAddresses()).thenReturn(Set.of(address));

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId))
                .thenReturn(parameterSupplier);
        Supplier<Address> addressSupplier = () -> address;
        Function<UserDetails, Address> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(UserDetails.class, Address.class))
                .thenReturn(function);
//        when(supplierService.entityFieldSupplier(
//                UserDetails.class,
//                parameterSupplier,
//                function
//        )).thenReturn(addressSupplier);

        addressManager.deleteAppUserAddress(userId);

        verify(addressDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testDeleteAppUserAddress_NullDto() {
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        doThrow(new RuntimeException()).when(addressDao).deleteEntity(any(Supplier.class));
        when(supplierService.parameterStringSupplier(eq("user_id"), any()))
                .thenReturn(parameterSupplier);
//        when(queryService.getEntity(eq(UserDetails.class), any(Supplier.class)))
//                .thenReturn(null);
        assertThrows(RuntimeException.class, () -> addressManager.deleteAppUserAddress(null));
    }

    @Test
    void testDeleteDeliveryAddress() {
        String orderId = "123";
        OrderArticularItemQuantity orderItemDataOption = mock(OrderArticularItemQuantity.class);
        Delivery delivery = mock(Delivery.class);
        Address address = getAddress();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId))
                .thenReturn(parameterSupplier);
        when(orderItemDataOption.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(address);
        Supplier<Address> addressSupplier = () -> address;
        Function<OrderArticularItemQuantity, Address> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderArticularItemQuantity.class, Address.class))
                .thenReturn(function);
//        when(supplierService.entityFieldSupplier(
//                OrderArticularItemQuantity.class,
//                parameterSupplier,
//                function
//        )).thenReturn(addressSupplier);

        addressManager.deleteDeliveryAddress(orderId);

        verify(addressDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testGetAddressByUserId() {
        String userId = "123";
        UserDetails userDetails = mock(UserDetails.class);
        Address address = getAddress();
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId)).thenReturn(parameterSupplier);

        Function<UserDetails, AddressDto> transformationFunction = user -> getAddressDto();
        when(transformationFunctionService.getTransformationFunction(UserDetails.class, AddressDto.class))
                .thenReturn(transformationFunction);

//        when(queryService.getEntityDto(eq(UserDetails.class), eq(parameterSupplier), eq(transformationFunction)))
//                .thenAnswer(invocation -> {
//                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
//                    Function<UserDetails, AddressDto> functionArg = invocation.getArgument(2);
//                    assertEquals(parameterSupplier.get(), supplierArg.get());
//                    return functionArg.apply(userDetails);
//                });

//        when(userDetails.getAddresses()).thenReturn(Set.of(address));

//        AddressDto addressDto = addressManager.getAddressByUserId(userId);
        AddressDto expectedAddressDto = getAddressDto();
//        assertEquals(expectedAddressDto, addressDto);
    }

    @Test
    void testGetAddressByOrderId() {
        String orderId = "12345";
        OrderArticularItemQuantity orderItemDataOption = mock(OrderArticularItemQuantity.class);
        Delivery delivery = mock(Delivery.class);
        Address address = getAddress();
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId)).thenReturn(parameterSupplier);

        Function<OrderArticularItemQuantity, AddressDto> transformationFunction = user -> getAddressDto();
        when(transformationFunctionService.getTransformationFunction(OrderArticularItemQuantity.class, AddressDto.class))
                .thenReturn(transformationFunction);

//        when(queryService.getEntityDto(eq(OrderArticularItemQuantity.class), eq(parameterSupplier), eq(transformationFunction)))
//                .thenAnswer(invocation -> {
//                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
//                    Function<OrderArticularItemQuantity, AddressDto> functionArg = invocation.getArgument(2);
//                    assertEquals(parameterSupplier.get(), supplierArg.get());
//                    return functionArg.apply(orderItemDataOption);
//                });
        when(orderItemDataOption.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(address);

        AddressDto addressDto = addressManager.getAddressByOrderId(orderId);
        AddressDto expectedAddressDto = getAddressDto();
        assertEquals(expectedAddressDto, addressDto);
    }

    @Test
    void testGetAddresses_EmptyList() {
//        when(addressDao.getEntityList()).thenReturn(Collections.emptyList());

        List<AddressDto> result = addressManager.getAddresses();

        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAddresses() {
        Address address1 = mock(Address.class);
        Address address2 = mock(Address.class);
        when(address1.getCountry()).thenReturn(mock(Country.class));
        when(address2.getCountry()).thenReturn(mock(Country.class));

        Function<Address, AddressDto> mockFunction = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(Address.class, AddressDto.class))
                .thenReturn(mockFunction);
//        when(addressDao.getEntityList()).thenReturn(List.of(address1, address2));
        List<AddressDto> result = addressManager.getAddresses();

        assertEquals(2, result.size());
    }

    private Address getAddress() {
        return Address.builder()
                .id(1)
                .country(getCountry())
                .city("city")
                .street("street")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91000")
                .build();
    }

    private Address getUpdatedAddress() {
        return Address.builder()
                .id(1)
                .country(getCountry())
                .city("city")
                .street("update street")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91001")
                .build();
    }

    private AddressDto getAddressDto() {
        return AddressDto.builder()
                .country("USA")
                .city("city")
                .street("street")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91000")
                .build();
    }

    private AddressDto getUpdateAddressDto() {
        return AddressDto.builder()
                .country("USA")
                .city("city")
                .street("update street")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91001")
                .build();
    }

    private Country getCountry() {
        return Country.builder()
                .value("USA")
                .build();
    }
}
