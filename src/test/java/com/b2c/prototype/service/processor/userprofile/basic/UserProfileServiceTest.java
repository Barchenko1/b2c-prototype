package com.b2c.prototype.service.processor.userprofile.basic;

import com.b2c.prototype.modal.dto.request.AddressDto;
import com.b2c.prototype.modal.dto.request.ContactInfoDto;
import com.b2c.prototype.modal.dto.request.ContactPhoneDto;
import com.b2c.prototype.modal.dto.request.RegistrationUserProfileDto;
import com.b2c.prototype.modal.dto.request.UserProfileDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserProfile;
import com.b2c.prototype.dao.user.IUserProfileDao;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.b2c.prototype.util.CardUtil;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserProfileServiceTest {

    @Mock
    private IUserProfileDao userProfileDao;
    @Mock
    private ITransformationFunctionService transformationFunctionService;
    @Mock
    private ISupplierService supplierService;
    @InjectMocks
    private UserProfileService userProfileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createNewUser_shouldSaveEntity() {
        RegistrationUserProfileDto registrationUserProfileDto = RegistrationUserProfileDto.builder()
                .email("email@email.com")
                .username("username")
                .password("password")
                .build();
        UserProfile userProfile = new UserProfile();

        when(transformationFunctionService.getEntity(UserProfile.class, registrationUserProfileDto))
                .thenReturn(userProfile);

        userProfileService.createNewUser(registrationUserProfileDto);

        verify(userProfileDao).mergeEntity(userProfile);
    }

    @Test
    void updateUserStatusByUserId_shouldUpdateStatus() {
        String userId = "test-user-id";
        boolean isActive = true;
        UserProfile userProfile = new UserProfile();
        userProfile.setActive(false);
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("user_id", userId))
                .thenReturn(parameterSupplier);
        when(userProfileDao.getEntity(parameter))
                .thenReturn(userProfile);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userProfile);
            return null;
        }).when(userProfileDao).executeConsumer(any(Consumer.class));

        userProfileService.updateUserStatusByUserId(userId, isActive);

        assertTrue(userProfile.isActive());
        verify(userProfileDao).executeConsumer(any(Consumer.class));
    }

    @Test
    void deleteUserProfileByUserId_shouldDeleteEntity() {
        String userId = "test-user-id";
        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("user_id", userId)).thenReturn(parameterSupplier);

        userProfileService.deleteUserProfileByUserId(userId);

        verify(userProfileDao).findEntityAndDelete(parameter);
    }

    @Test
    void getUserProfileByUserId_shouldReturnUserDetailsDto() {
        String userId = "test-user-id";
        UserProfileDto userProfileDto = getUserProfileDto();
        UserProfile userProfile = getUserProfile();

        Parameter parameter = mock(Parameter.class);
        Supplier<Parameter> parameterSupplier = () -> parameter;
        when(supplierService.parameterStringSupplier("user_id", userId))
                .thenReturn(parameterSupplier);
        when(transformationFunctionService.getTransformationFunction(UserProfile.class, UserProfileDto.class))
                .thenReturn(transformationFunction);
        when(userProfileDao.getEntity(parameter))
                .thenReturn(userProfile);

        UserProfileDto result = userProfileService.getUserProfileByUserId(userId);

        assertEquals(userProfileDto, result);
    }

    @Test
    void getUserProfiles_shouldReturnListOfUserDetailsDto() {
        UserProfileDto userProfileDto = getUserProfileDto();
        UserProfile userProfile = getUserProfile();

        when(transformationFunctionService.getTransformationFunction(UserProfile.class, UserProfileDto.class))
                .thenReturn(transformationFunction);
        when(userProfileDao.getEntityList())
                .thenReturn(Collections.singletonList(userProfile));

        List<UserProfileDto> result = userProfileService.getUserProfiles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userProfileDto, result.get(0));
    }

    private UserProfileDto getUserProfileDto() {
        return UserProfileDto.builder()
                .addressDto(AddressDto.builder()
                        .country("+11")
                        .city("city")
                        .street("street")
                        .street2("street2")
                        .buildingNumber(1)
                        .florNumber(9)
                        .apartmentNumber(101)
                        .zipCode("90000")
                        .build())
                .email("email@email.com")
                .contactInfo(ContactInfoDto.builder()
                        .name("name")
                        .secondName("secondName")
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode("+11")
                                .phoneNumber("phoneNumber")
                                .build())
                        .build())
                .creditCards(List.of(ResponseCreditCardDto.builder()
                        .cardNumber("4444-1111-2222-3333")
                        .dateOfExpire("06/28")
                        .ownerName("name")
                        .ownerSecondName("secondName")
                        .build()))
                .postList(List.of())
                .build();
    }

    private UserProfile getUserProfile() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .phoneNumber("phoneNumber")
                .countryPhoneCode(countryPhoneCode)
                .build();
        ContactInfo contactInfo = ContactInfo.builder()
                .id(1L)
                .name("name")
                .secondName("secondName")
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
                .street2("street2")
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv("818")
                .build();
        return UserProfile.builder()
                .id(1L)
                .username("username")
                .email("email@email.com")
                .dateOfCreate(100)
                .isActive(true)
                .contactInfo(contactInfo)
                .address(address)
                .creditCardList(List.of(creditCard))
                .postList(List.of())
                .build();
    }

    Function<UserProfile, UserProfileDto> transformationFunction =userProfile -> {
        ContactInfo contactInfo = userProfile.getContactInfo();
        ContactInfoDto contactInfoDto = ContactInfoDto.builder()
                .contactPhone(ContactPhoneDto.builder()
                        .phoneNumber(contactInfo.getContactPhone().getPhoneNumber())
                        .countryPhoneCode(contactInfo.getContactPhone().getCountryPhoneCode().getCode())
                        .build())
                .name(userProfile.getContactInfo().getName())
                .secondName(userProfile.getContactInfo().getSecondName())
                .build();
        Address address = userProfile.getAddress();
        AddressDto addressDto = AddressDto.builder()
                .apartmentNumber(address.getApartmentNumber())
                .buildingNumber(address.getBuildingNumber())
                .city(address.getCity())
                .zipCode(address.getZipCode())
                .street(address.getStreet())
                .street2(address.getStreet2())
                .florNumber(address.getFlorNumber())
                .country(address.getCountry().getValue())
                .build();
        CreditCard creditCard = userProfile.getCreditCardList().get(0);
        ResponseCreditCardDto creditCardDto = ResponseCreditCardDto.builder()
                .cardNumber(creditCard.getCardNumber())
                .dateOfExpire(creditCard.getDateOfExpire())
                .ownerSecondName(creditCard.getOwnerSecondName())
                .ownerName(creditCard.getOwnerName())
                .build();
        return UserProfileDto.builder()
                .email(userProfile.getEmail())
                .contactInfo(contactInfoDto)
                .creditCards(List.of(creditCardDto))
                .addressDto(addressDto)
                .postList(List.of())
                .build();
    };
}
