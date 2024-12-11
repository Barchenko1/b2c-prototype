package com.b2c.prototype.service.processor.address.base;

import com.b2c.prototype.dao.address.IAddressDao;
import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.order.IOrderItemDao;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.update.AddressDtoUpdate;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.order.OrderItem;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.service.processor.query.IQueryService;
import com.tm.core.processor.finder.factory.IParameterFactory;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

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
    private IParameterFactory parameterFactory;
    @Mock
    private IUserProfileDao userProfileDao;
    @Mock
    private IOrderItemDao orderItemDao;
    @Mock
    private IAddressDao addressDao;
    @Mock
    private IQueryService queryService;
    @Mock
    private IEntityCachedMap entityCachedMap;
    @InjectMocks
    private AddressService addressService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveAddress() {
        AddressDto addressDto = getAddressDto();
        when(entityCachedMap.getEntity(eq(Country.class), eq("value"), eq("USA")))
                .thenReturn(getCountry());
        addressService.saveAddress(addressDto);

        ArgumentCaptor<Supplier<Address>> captor = ArgumentCaptor.forClass(Supplier.class);
        verify(addressDao).saveEntity(captor.capture());
        Address capturedEntity = captor.getValue().get();
        assertEquals(addressDto.getStreet(), capturedEntity.getStreet());
    }

    @Test
    void testSaveAddress_NullDto() {
        when(entityCachedMap.getEntity(eq(Country.class), eq("value"), eq("USA")))
                .thenReturn(getCountry());
        doThrow(new RuntimeException()).when(addressDao).saveEntity(any(Supplier.class));
        assertThrows(RuntimeException.class, () -> addressService.saveAddress(null));
    }

    @Test
    void testUpdateAppUserAddress() {
        AddressDtoUpdate addressDtoUpdate = new AddressDtoUpdate();
        AddressDto newAddressDto = AddressDto.builder()
                .country("USA")
                .city("city")
                .street("update street")
                .street2("update street2")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91001")
                .build();
        addressDtoUpdate.setSearchField("search");
        addressDtoUpdate.setNewEntityDto(newAddressDto);
        UserProfile userProfile = mock(UserProfile.class);

        when(parameterFactory.createStringParameter("username", addressDtoUpdate.getSearchField()))
                .thenReturn(mock(Parameter.class));
        when(entityCachedMap.getEntity(eq(Country.class), eq("value"), eq("USA")))
                .thenReturn(getCountry());
        when(queryService.getEntity(eq(UserProfile.class), any(Supplier.class))).thenReturn(userProfile);
        when(userProfile.getAddress()).thenReturn(getAddress());

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(getUpdatedAddress());
            return null;
        }).when(addressDao).updateEntity(any(Consumer.class));

        addressService.updateAppUserAddress(addressDtoUpdate);

        verify(addressDao).updateEntity(any(Consumer.class));
    }

    @Test
    void testUpdateDeliveryAddress() {
        AddressDtoUpdate addressDtoUpdate = new AddressDtoUpdate();
        AddressDto newAddressDto = AddressDto.builder()
                .country("USA")
                .city("city")
                .street("update street")
                .street2("update street2")
                .buildingNumber(1)
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91001")
                .build();
        addressDtoUpdate.setSearchField("search");
        addressDtoUpdate.setNewEntityDto(newAddressDto);

        OrderItem orderItem = mock(OrderItem.class);
        Address address = mock(Address.class);
        when(address.getCountry()).thenReturn(mock(Country.class));

        Delivery delivery = mock(Delivery.class);

        when(parameterFactory.createStringParameter("order_id", addressDtoUpdate.getSearchField()))
                .thenReturn(mock(Parameter.class));
        when(entityCachedMap.getEntity(eq(Country.class), eq("value"), eq("USA")))
                .thenReturn(getCountry());
        when(queryService.getEntity(eq(OrderItem.class), any(Supplier.class))).thenReturn(orderItem);
        when(orderItem.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(getAddress());

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(getUpdatedAddress());
            return null;
        }).when(addressDao).updateEntity(any(Consumer.class));

        addressService.updateDeliveryAddress(addressDtoUpdate);

        verify(addressDao).updateEntity(any(Consumer.class));
    }

    @Test
    void testDeleteAppUserAddress() {
        OneFieldEntityDto dto = new OneFieldEntityDto("email");

        Parameter parameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("email", dto.getValue())).thenReturn(parameter);

        addressService.deleteAppUserAddress(dto);

        verify(addressDao).findEntityAndDelete(parameter);
    }

    @Test
    void testDeleteAppUserAddress_NullDto() {
        assertThrows(NullPointerException.class, () -> addressService.deleteAppUserAddress(null));
    }

    @Test
    void testDeleteDeliveryAddress() {
        OneFieldEntityDto dto = new OneFieldEntityDto("order_id");

        Parameter parameter = mock(Parameter.class);
        when(parameterFactory.createStringParameter("order_id", dto.getValue()))
                .thenReturn(parameter);

        addressService.deleteDeliveryAddress(dto);

        verify(addressDao).findEntityAndDelete(parameter);
    }

    @Test
    void testGetAddressByEmail() {
        String email = "test@example.com";
        UserProfile userProfile = mock(UserProfile.class);
        Address address = getAddress();
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(email);

        when(parameterFactory.createStringParameter("email", email))
                .thenReturn(parameter);
        when(queryService.getEntityDto(eq(UserProfile.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<UserProfile, AddressDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(parameter, paramSupplier.get());
                    return mappingFunction.apply(userProfile);
                });
        when(userProfile.getAddress()).thenReturn(address);
        AddressDto addressDto = addressService.getAddressByEmail(oneFieldEntityDto);

        AddressDto expectedAddressDto = getAddressDto();
        assertEquals(expectedAddressDto, addressDto);
    }

    @Test
    void testGetAddressByUsername() {
        String username = "username";
        UserProfile userProfile = mock(UserProfile.class);
        Address address = getAddress();
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(username);

        when(parameterFactory.createStringParameter("username", username))
                .thenReturn(parameter);
        when(queryService.getEntityDto(eq(UserProfile.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<UserProfile, AddressDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(parameter, paramSupplier.get());
                    return mappingFunction.apply(userProfile);
                });
        when(userProfile.getAddress()).thenReturn(address);
        AddressDto addressDto = addressService.getAddressByUsername(oneFieldEntityDto);

        AddressDto expectedAddressDto = getAddressDto();
        assertEquals(expectedAddressDto, addressDto);
    }

    @Test
    void testGetAddressByOrderId() {
        String orderId = "12345";
        OrderItem orderItem = mock(OrderItem.class);
        Delivery delivery = mock(Delivery.class);
        Address address = getAddress();
        Parameter parameter = mock(Parameter.class);
        OneFieldEntityDto oneFieldEntityDto = new OneFieldEntityDto(orderId);

        when(parameterFactory.createStringParameter("order_id", orderId))
                .thenReturn(parameter);
        when(orderItem.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(address);
        when(queryService.getEntityDto(eq(OrderItem.class), any(Supplier.class), any(Function.class)))
                .thenAnswer(invocation -> {
                    Supplier<Parameter> paramSupplier = invocation.getArgument(1);
                    Function<OrderItem, AddressDto> mappingFunction = invocation.getArgument(2);
                    assertEquals(parameter, paramSupplier.get());
                    return mappingFunction.apply(orderItem);
                });
        when(orderItem.getDelivery()).thenReturn(delivery);
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

    private Country getCountry() {
        return Country.builder()
                .value("USA")
                .build();
    }
}
