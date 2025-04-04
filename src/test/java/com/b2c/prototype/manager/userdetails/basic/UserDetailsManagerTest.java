package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserDetailsDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.dao.user.IUserDetailsDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.util.CardUtil;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDetailsManagerTest {

    @Mock
    private IUserDetailsDao userDetailsDao;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @InjectMocks
    private UserDetailsManager userDetailsManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewUser_shouldSaveEntity() {
        RegistrationUserDetailsDto registrationUserDetailsDto = RegistrationUserDetailsDto.builder()
                .email("email@email.com")
                .password("password")
                .build();
        UserDetails userDetails = new UserDetails();

        when(transformationFunctionService.getEntity(UserDetails.class, registrationUserDetailsDto))
                .thenReturn(userDetails);

        userDetailsManager.createNewUser(registrationUserDetailsDto);

        verify(userDetailsDao).mergeEntity(userDetails);
    }

    @Test
    void updateUserStatusByUserId_shouldUpdateStatus() {
        String userId = "test-user-id";
        boolean isActive = true;
        UserDetails userDetails = new UserDetails();
        userDetails.setActive(false);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
//        when(userDetailsDao.getNamedQueryEntity("", parameter))
//                .thenReturn(userDetails);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        }).when(userDetailsDao).executeConsumer(any(Consumer.class));

        userDetailsManager.updateUserStatusByUserId(userId, isActive);

        assertTrue(userDetails.isActive());
        verify(userDetailsDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteUserDetailsByUserId_shouldDeleteEntity() {
        String userId = "test-user-id";
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        

        userDetailsManager.deleteUserDetailsByUserId(userId);

        verify(userDetailsDao).findEntityAndDelete(parameter);
    }

    @Test
    void getUserDetailsByUserId_shouldReturnUserDetailsDto() {
        String userId = "test-user-id";
        UserDetailsDto userDetailsDto = getUserDetailsDto();
        UserDetails userDetails = getUserDetails();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(transformationFunctionService.getTransformationFunction(UserDetails.class, UserDetailsDto.class))
                .thenReturn(transformationFunction);
        when(userDetailsDao.getGraphEntity(anyString(), eq(parameter)))
                .thenReturn(userDetails);

        ResponseUserDetailsDto result = userDetailsManager.getUserDetailsByUserId(userId);

        assertEquals(userDetailsDto, result);
    }

    @Test
    void getUserDetails_shouldReturnListOfUserDetailsDto() {
        UserDetailsDto userDetailsDto = getUserDetailsDto();
        UserDetails userDetails = getUserDetails();

        when(transformationFunctionService.getTransformationFunction(UserDetails.class, UserDetailsDto.class))
                .thenReturn(transformationFunction);
//        when(userDetailsDao.getEntityList()).thenReturn(Collections.singletonList(userDetails));

        List<ResponseUserDetailsDto> result = userDetailsManager.getResponseUserDetails();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDetailsDto, result.get(0));
    }

    private UserDetailsDto getUserDetailsDto() {
        return UserDetailsDto.builder()
                .address(AddressDto.builder()
                        .country("+11")
                        .city("city")
                        .street("street")
                        .buildingNumber("1")
                        .florNumber(9)
                        .apartmentNumber(101)
                        .zipCode("90000")
                        .build())
                .contactInfo(ContactInfoDto.builder()
                        .firstName("name")
                        .lastName("lastName")
                        .email("email@email.com")
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode("+11")
                                .phoneNumber("phoneNumber")
                                .build())
                        .build())
                .creditCard(CreditCardDto.builder()
                        .cardNumber("4444-1111-2222-3333")
                        .monthOfExpire(6)
                        .yearOfExpire(28)
                        .ownerName("name")
                        .ownerSecondName("secondName")
                        .build())
                .build();
    }

    private UserDetails getUserDetails() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .phoneNumber("phoneNumber")
                .countryPhoneCode(countryPhoneCode)
                .build();
        ContactInfo contactInfo = ContactInfo.builder()
                .id(1L)
                .firstName("name")
                .lastName("lastName")
                .contactPhone(contactPhone)
                .build();
        Country country = Country.builder()
                .id(1L)
                .value("+11")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .city("city")
                .street("street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
        UserCreditCard userCreditCard = UserCreditCard.builder()
                .creditCard(creditCard)
                .isDefault(false)
                .build();
        return UserDetails.builder()
                .id(1L)
                .username("username")
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(contactInfo)
//                .addresses(Set.of(address))
                .userCreditCards(Set.of(userCreditCard))
                .build();
    }

    Function<UserDetails, UserDetailsDto> transformationFunction = userDetails -> {
        ContactInfo contactInfo = userDetails.getContactInfo();
        ContactInfoDto contactInfoDto = ContactInfoDto.builder()
                .contactPhone(ContactPhoneDto.builder()
                        .phoneNumber(contactInfo.getContactPhone().getPhoneNumber())
                        .countryPhoneCode(contactInfo.getContactPhone().getCountryPhoneCode().getValue())
                        .build())
                .firstName(userDetails.getContactInfo().getFirstName())
                .lastName(userDetails.getContactInfo().getLastName())
                .build();
        Address address = (Address) userDetails.getUserAddresses().stream().toList().get(0).getAddress();
        AddressDto addressDto = AddressDto.builder()
                .apartmentNumber(address.getApartmentNumber())
                .buildingNumber(address.getBuildingNumber())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .street(address.getStreet())
                .florNumber(address.getFlorNumber())
                .country(address.getCountry().getValue())
                .build();
        UserCreditCard userCreditCard = userDetails.getUserCreditCards().stream().toList().get(0);
        CreditCard creditCard = userCreditCard.getCreditCard();
        CreditCardDto creditCardDto = CreditCardDto.builder()
                .cardNumber(creditCard.getCardNumber())
                .monthOfExpire(creditCard.getMonthOfExpire())
                .yearOfExpire(creditCard.getYearOfExpire())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .ownerName(creditCard.getOwnerName())
                .build();
        return UserDetailsDto.builder()
                .contactInfo(contactInfoDto)
                .creditCard(creditCardDto)
                .address(addressDto)
                .build();
    };
}
