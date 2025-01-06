package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.searchfield.AddressSearchFieldEntityDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.OrderItemData;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
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

class AddressServiceTest {

    @Mock
    private IAddressDao addressDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateAppUserAddress() {
        AddressSearchFieldEntityDto addressSearchFieldEntityDto = getAddressSearchFieldDto();
        UserProfile userProfile = mock(UserProfile.class);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, addressSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(UserProfile.class, parameterSupplier))
                .thenReturn(userProfile);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(addressDao).executeConsumer(any(Consumer.class));

        addressService.saveUpdateAppUserAddress(addressSearchFieldEntityDto);

        verify(addressDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testSaveUpdateDeliveryAddress() {
        AddressSearchFieldEntityDto addressSearchFieldEntityDto = getAddressSearchFieldDto();
        OrderItemData orderItemData = mock(OrderItemData.class);
        Delivery delivery = mock(Delivery.class);
        when(orderItemData.getDelivery()).thenReturn(delivery);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, addressSearchFieldEntityDto.getSearchField()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(OrderItemData.class, parameterSupplier))
                .thenReturn(orderItemData);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(delivery);
            return null;
        }).when(addressDao).executeConsumer(any(Consumer.class));

        addressService.saveUpdateDeliveryAddress(addressSearchFieldEntityDto);

        verify(addressDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteAppUserAddress() {
        OneFieldEntityDto dto = new OneFieldEntityDto("userId");
        UserProfile userProfile = mock(UserProfile.class);
        Address address = mock(Address.class);

        when(userProfile.getAddress()).thenReturn(address);

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, dto.getValue()))
                .thenReturn(parameterSupplier);
        Supplier<Address> addressSupplier = () -> address;
        Function<UserProfile, Address> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(UserProfile.class, Address.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                UserProfile.class,
                parameterSupplier,
                function
        )).thenReturn(addressSupplier);

        addressService.deleteAppUserAddress(dto);

        verify(addressDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testDeleteAppUserAddress_NullDto() {
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;

        doThrow(new RuntimeException()).when(addressDao).deleteEntity(any(Supplier.class));
        when(supplierService.parameterStringSupplier(eq("user_id"), any()))
                .thenReturn(parameterSupplier);
        when(queryService.getEntity(eq(UserProfile.class), any(Supplier.class)))
                .thenReturn(null);
        assertThrows(RuntimeException.class, () -> addressService.deleteAppUserAddress(null));
    }

    @Test
    void testDeleteDeliveryAddress() {
        OneFieldEntityDto dto = new OneFieldEntityDto("123");
        OrderItemData orderItemData = mock(OrderItemData.class);
        Delivery delivery = mock(Delivery.class);
        Address address = getAddress();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, dto.getValue()))
                .thenReturn(parameterSupplier);
        when(orderItemData.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(address);
        Supplier<Address> addressSupplier = () -> address;
        Function<OrderItemData, Address> function = mock(Function.class);
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, Address.class))
                .thenReturn(function);
        when(supplierService.entityFieldSupplier(
                OrderItemData.class,
                parameterSupplier,
                function
        )).thenReturn(addressSupplier);

        addressService.deleteDeliveryAddress(dto);

        verify(addressDao).deleteEntity(any(Supplier.class));
    }

    @Test
    void testGetAddressByUserId() {
        String userId = "123";
        UserProfile userProfile = mock(UserProfile.class);
        Address address = getAddress();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(userId);
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(USER_ID, userId)).thenReturn(parameterSupplier);

        Function<UserProfile, AddressDto> transformationFunction = user -> getAddressDto();
        when(transformationFunctionService.getTransformationFunction(UserProfile.class, AddressDto.class))
                .thenReturn(transformationFunction);

        when(queryService.getEntityDto(eq(UserProfile.class), eq(parameterSupplier), eq(transformationFunction)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<UserProfile, AddressDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(userProfile);
                });

        when(userProfile.getAddress()).thenReturn(address);

        AddressDto addressDto = addressService.getAddressByUserId(oneFieldEntityDto);
        AddressDto expectedAddressDto = getAddressDto();
        assertEquals(expectedAddressDto, addressDto);
    }

    @Test
    void testGetAddressByOrderId() {
        String orderId = "12345";
        OrderItemData orderItemData = mock(OrderItemData.class);
        Delivery delivery = mock(Delivery.class);
        Address address = getAddress();
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);
        Parameter parameter = mock(Parameter.class);

        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier(ORDER_ID, orderId)).thenReturn(parameterSupplier);

        Function<OrderItemData, AddressDto> transformationFunction = user -> getAddressDto();
        when(transformationFunctionService.getTransformationFunction(OrderItemData.class, AddressDto.class))
                .thenReturn(transformationFunction);

        when(queryService.getEntityDto(eq(OrderItemData.class), eq(parameterSupplier), eq(transformationFunction)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
                    Function<OrderItemData, AddressDto> functionArg = invocation.getArgument(2);
                    assertEquals(parameterSupplier.get(), supplierArg.get());
                    return functionArg.apply(orderItemData);
                });
        when(orderItemData.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(address);

        AddressDto addressDto = addressService.getAddressByOrderId(oneFieldEntityDto);
        AddressDto expectedAddressDto = getAddressDto();
        assertEquals(expectedAddressDto, addressDto);
    }

    @Test
    void testGetAddresses_EmptyList() {
        when(addressDao.getEntityList())
                .thenReturn(Collections.emptyList());

        List<AddressDto> result = addressService.getAddresses();

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
        when(addressDao.getEntityList())
                .thenReturn(List.of(address1, address2));
        List<AddressDto> result = addressService.getAddresses();

        assertEquals(2, result.size());
    }

    private Address getAddress() {
        return Address.builder()
                .id(1)
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

    private Address getUpdatedAddress() {
        return Address.builder()
                .id(1)
                .country(getCountry())
                .city("city")
                .street("update street")
                .street2("update street2")
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
                .street2("street2")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91000")
                .build();
    }

    private AddressSearchFieldEntityDto getAddressSearchFieldDto() {
        return AddressSearchFieldEntityDto.builder()
                .searchField("123")
                .newEntity(AddressDto.builder()
                        .country("USA")
                        .city("city")
                        .street("street")
                        .street2("street2")
                        .buildingNumber(1)
                        .florNumber(9)
                        .apartmentNumber(101)
                        .zipCode("91000")
                        .build())
                .build();
    }

    private AddressDto getUpdateAddressDto() {
        return AddressDto.builder()
                .country("USA")
                .city("city")
                .street("update street")
                .street2("update street2")
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
