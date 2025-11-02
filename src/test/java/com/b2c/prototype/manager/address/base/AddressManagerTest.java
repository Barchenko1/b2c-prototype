package com.b2c.prototype.manager.address.base;

import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.order.DeliveryArticularItemQuantity;
import com.b2c.prototype.modal.entity.user.UserDetails;


import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AddressManagerTest {
    @InjectMocks
    private UserAddressManager addressManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveUpdateAppUserAddress() {
        String userId = "123";
        UserAddressDto userAddressDto = getUserAddressDto();
        UserDetails userDetails = mock(UserDetails.class);

        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        });

        addressManager.saveUserAddress(userId, userAddressDto);

    }

    @Test
    void testSaveUpdateDeliveryAddress() {
        String orderId = "123";
        AddressDto addressDto = getAddressDto();
        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Delivery delivery = mock(Delivery.class);
        when(orderItemDataOption.getDelivery()).thenReturn(delivery);


        
//        when(queryService.getEntity(DeliveryArticularItemQuantity.class, parameterSupplier))
//                .thenReturn(orderItemDataOption);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(delivery);
            return null;
        });

//        addressManager.saveUserAddress(orderId, addressDto);

//        verify(addressDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void testDeleteAppUserAddress() {
        String userId = "userId";
        UserDetails userDetails = mock(UserDetails.class);
        Address address = mock(Address.class);

//        when(userDetails.getAddresses()).thenReturn(Set.of(address));

//        when(supplierService.entityFieldSupplier(
//                UserDetails.class,
//                parameterSupplier,
//                function
//        )).thenReturn(addressSupplier);

//        addressManager.deleteAppUserAddress(userId);

    }

    @Test
    void testDeleteAppUserAddress_NullDto() {


//        doThrow(new RuntimeException()).when(addressDao).deleteEntity(any(Supplier.class));

//        when(queryService.getEntity(eq(UserDetails.class), any(Supplier.class)))
//                .thenReturn(null);
//        assertThrows(RuntimeException.class, () -> addressManager.deleteAppUserAddress(null));
    }

    @Test
    void testDeleteDeliveryAddress() {
        String orderId = "123";
        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Delivery delivery = mock(Delivery.class);
        Address address = getAddress();


        
        when(orderItemDataOption.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(address);
        Supplier<Address> addressSupplier = () -> address;
        Function<DeliveryArticularItemQuantity, Address> function = mock(Function.class);
//        when(supplierService.entityFieldSupplier(
//                DeliveryArticularItemQuantity.class,
//                parameterSupplier,
//                function
//        )).thenReturn(addressSupplier);

//        addressManager.deleteDeliveryAddress(orderId);

    }

    @Test
    void testGetAddressByUserId() {
        String userId = "123";
        UserDetails userDetails = mock(UserDetails.class);
        Address address = getAddress();

        Function<UserDetails, AddressDto> transformationFunction = user -> getAddressDto();
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
        DeliveryArticularItemQuantity orderItemDataOption = mock(DeliveryArticularItemQuantity.class);
        Delivery delivery = mock(Delivery.class);
        Address address = getAddress();

        Function<DeliveryArticularItemQuantity, AddressDto> transformationFunction = user -> getAddressDto();
//        when(queryService.getEntityDto(eq(DeliveryArticularItemQuantity.class), eq(parameterSupplier), eq(transformationFunction)))
//                .thenAnswer(invocation -> {
//                    Supplier<Parameter> supplierArg = invocation.getArgument(1);
//                    Function<DeliveryArticularItemQuantity, AddressDto> functionArg = invocation.getArgument(2);
//                    assertEquals(parameterSupplier.get(), supplierArg.get());
//                    return functionArg.apply(orderItemDataOption);
//                });
        when(orderItemDataOption.getDelivery()).thenReturn(delivery);
        when(delivery.getAddress()).thenReturn(address);

//        AddressDto addressDto = addressManager.getAllAddressesByAddress(orderId);
        AddressDto expectedAddressDto = getAddressDto();
//        assertEquals(expectedAddressDto, addressDto);
    }

    @Test
    void testGetAddresses_EmptyList() {
//        when(addressDao.getEntityList()).thenReturn(Collections.emptyList());

//        List<AddressDto> result = addressManager.getAddresses();

//        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAddresses() {
        Address address1 = mock(Address.class);
        Address address2 = mock(Address.class);
        when(address1.getCountry()).thenReturn(mock(Country.class));
        when(address2.getCountry()).thenReturn(mock(Country.class));

        Function<Address, AddressDto> mockFunction = mock(Function.class);
//        when(addressDao.getEntityList()).thenReturn(List.of(address1, address2));
//        List<AddressDto> result = addressManager.getAddresses();

//        assertEquals(2, result.size());
    }

    private Address getAddress() {
        return Address.builder()
                .id(1)
                .country(getCountry())
                .city("city")
                .street("street")
                .buildingNumber("1")
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
                .buildingNumber("1")
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91001")
                .build();
    }

    private AddressDto getAddressDto() {
        return AddressDto.builder()
                .country(CountryDto.builder()
                        .value("USA")
                        .key("USA")
                        .build())
                .city("city")
                .street("street")
                .buildingNumber("1")
                .florNumber(9)
                .apartmentNumber(101)
                .zipCode("91000")
                .build();
    }

    private UserAddressDto getUserAddressDto() {
        return UserAddressDto.builder()
                .address(getAddressDto())
                .isDefault(false)
                .build();
    }

    private AddressDto getUpdateAddressDto() {
        return AddressDto.builder()
//                .country("USA")
                .city("city")
                .street("update street")
                .buildingNumber("1")
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
