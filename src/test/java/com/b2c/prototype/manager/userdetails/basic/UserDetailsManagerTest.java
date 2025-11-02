package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.util.CardUtil;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class UserDetailsManagerTest {
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

        userDetailsManager.createNewUser(registrationUserDetailsDto);

//        verify(userDetailsDao).mergeEntity(userDetails);
    }

    @Test
    void updateUserStatusByUserId_shouldUpdateStatus() {
        String userId = "test-user-id";
        boolean isActive = true;
        UserDetails userDetails = new UserDetails();
        userDetails.setActive(false);

//        when(userDetailsDao.getNamedQueryEntity("", parameter))
//                .thenReturn(userDetails);
        doAnswer(invocation -> {
            Consumer<Session> consumer = invocation.getArgument(0);
            Session session = mock(Session.class);
            consumer.accept(session);
            verify(session).merge(userDetails);
            return null;
        });

//        userDetailsManager.updateUserDetailsStatus(userId, isActive);

        assertTrue(userDetails.isActive());
    }

    @Test
    void getUserDetailsByUserId_shouldReturnUserDetailsDto() {
        String userId = "test-user-id";
        UserDetailsDto userDetailsDto = getUserDetailsDto();
        UserDetails userDetails = getUserDetails();

        UserDetailsDto result = userDetailsManager.getUserDetailsByUserId(userId);

        assertEquals(userDetailsDto, result);
    }

    @Test
    void getUserDetails_shouldReturnListOfUserDetailsDto() {
        UserDetailsDto userDetailsDto = getUserDetailsDto();
        UserDetails userDetails = getUserDetails();

        List<UserDetailsDto> result = userDetailsManager.getUserDetails();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userDetailsDto, result.get(0));
    }

    private UserDetailsDto getUserDetailsDto() {
        return UserDetailsDto.builder()
//                .address(AddressDto.builder()
////                        .country("+11")
//                        .city("city")
//                        .street("street")
//                        .buildingNumber("1")
//                        .florNumber(9)
//                        .apartmentNumber(101)
//                        .zipCode("90000")
//                        .build())
                .contactInfo(ContactInfoDto.builder()
                        .firstName("name")
                        .lastName("lastName")
                        .email("email@email.com")
                        .contactPhone(ContactPhoneDto.builder()
//                                .countryPhoneCode("+11")
                                .phoneNumber("phoneNumber")
                                .build())
                        .build())
//                .creditCard(CreditCardDto.builder()
//                        .cardNumber("4444-1111-2222-3333")
//                        .monthOfExpire(6)
//                        .yearOfExpire(28)
//                        .ownerName("name")
//                        .ownerSecondName("secondName")
//                        .build())
                .build();
    }

    private UserDetails getUserDetails() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .key("+11")
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
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .isActive(true)
                .contactInfo(contactInfo)
//                .addresses(Set.of(address))
                .userCreditCards(Set.of(userCreditCard))
                .build();
    }

}
