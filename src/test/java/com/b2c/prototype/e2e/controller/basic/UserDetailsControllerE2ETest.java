package com.b2c.prototype.e2e.controller.basic;

import com.b2c.prototype.e2e.BasicE2ETest;
import com.b2c.prototype.modal.dto.payload.constant.CountryDto;
import com.b2c.prototype.modal.dto.payload.constant.CountryPhoneCodeDto;
import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ContactInfoDto;
import com.b2c.prototype.modal.dto.payload.order.ContactPhoneDto;
import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.DeviceDto;
import com.b2c.prototype.modal.dto.payload.user.RegistrationUserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserAddressDto;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsAddCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsContactInfoDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsRemoveCollectionDto;
import com.b2c.prototype.modal.dto.payload.user.UserDetailsStatusDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDate;

import java.util.List;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDetailsControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/user/details";

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE user_details RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE contact_info RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE address RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE credit_card RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testE2ERegistrationUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"user_id", "date_of_create"})
    void testCreateNewUserDetails() {
        RegistrationUserDetailsDto dto = getRegistrationUserDetailsDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/new")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testUpdateContactInfoE2EUserDetailsDataSet.yml", ignoreCols = {"id"})
    void testPostUserDetailsContactInfo() {
        UserDetailsContactInfoDto dto = getUpdateUserDetailsContactInfoDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/info")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", ignoreCols = {"id"})
    @Sql(statements = {
            "ALTER SEQUENCE address_id_seq RESTART WITH 3",
            "ALTER SEQUENCE user_address_id_seq RESTART WITH 3",
            "ALTER SEQUENCE credit_card_id_seq RESTART WITH 3",
            "ALTER SEQUENCE user_credit_card_id_seq RESTART WITH 3",
            "ALTER SEQUENCE device_id_seq RESTART WITH 3"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testPostUserDetailsMore() {
        UserDetailsDto dto = getUpdateUserDetailsDtoMore();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateLessE2EUserDetailsDataSet.yml", ignoreCols = {"id"})
    void testPostUserDetailsLess() {
        UserDetailsDto dto = getUpdateUserDetailsDtoLess();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testAddNewUserCreditCardE2EUserDetailsDataSet.yml", orderBy = {"id"})
    @Sql(statements = {
            "ALTER SEQUENCE credit_card_id_seq RESTART WITH 3",
            "ALTER SEQUENCE user_credit_card_id_seq RESTART WITH 3",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAddNewUserCreditCard() {
        UserDetailsAddCollectionDto dto = getNewUserDetailsCreditCardDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/add/creditcard")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testAddExistingUserCreditCardE2EUserDetailsDataSet.yml", orderBy = {"id"})
    void testAddExistingUserCreditCard() {
        UserDetailsAddCollectionDto dto = getExistingUserDetailsCreditCardDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/add/creditcard")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testDeleteCreditCardE2EUserDetailsDataSet.yml", orderBy = {"id"})
    void testDeleteUserCreditCard() {
        UserDetailsRemoveCollectionDto dto = getUserDetailsRemoveCreditCardCollectionDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/remove/creditcard")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testAddNewUserAddressE2EUserDetailsDataSet.yml", orderBy = {"id"})
    @Sql(statements = {
            "ALTER SEQUENCE address_id_seq RESTART WITH 3",
            "ALTER SEQUENCE user_address_id_seq RESTART WITH 3",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAddNewUserAddress() {
        UserDetailsAddCollectionDto dto = getNewUserDetailsAddressDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/add/address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testAddExistingUserAddressE2EUserDetailsDataSet.yml", orderBy = {"id"})
    void testAddExistingUserAddress() {
        UserDetailsAddCollectionDto dto = getExistingUserDetailsAddressDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/add/address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testDeleteAddressE2EUserDetailsDataSet.yml", orderBy = {"id"})
    void testDeleteUserAddress() {
        UserDetailsRemoveCollectionDto dto = getUserDetailsRemoveAddressCollectionDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/remove/address")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testAddNewDeviceE2EUserDetailsDataSet.yml",
            orderBy = {"id"},
            ignoreCols = {"login_time"})
    @Sql(statements = {
            "ALTER SEQUENCE device_id_seq RESTART WITH 3"
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void testAddNewUserDevice() {
        UserDetailsAddCollectionDto dto = getNewUserDetailsDeviceDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/add/device")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testAddExistingDeviceE2EUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"login_time"})
    void testAddExistingUserDevice() {
        UserDetailsAddCollectionDto dto = getExistingUserDetailsDeviceDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/add/device")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateStatusE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPostUserDetailsStatus() {
        UserDetailsStatusDto dto = getUserDetailsStatusDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateVerifyEmailE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPostUserDetailsVerifyEmail() {
        UserDetailsStatusDto dto = getUserDetailsStatusDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/verifyEmail")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateVerifyPhoneE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPostUserDetailsVerifyPhone() {
        UserDetailsStatusDto dto = getUserDetailsStatusDto();
        String jsonPayload = writeValueAsString(dto);

        webTestClient.post()
                .uri(URL_TEMPLATE + "/verifyPhone")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", orderBy = "id")
    void testDeleteUserDetails() {
        webTestClient.delete()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    void testGetUserDetails() {
        UserDetailsDto dto = getUserDetailsDto2();

        UserDetailsDto actual = webTestClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(UserDetailsDto.class)
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(dto);
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    void testGetAllUserDetails() {
        List<UserDetailsDto> constantPayloadDtoList = List.of(
                getUserDetailsDto(), getUserDetailsDto2()
        );
        List<UserDetailsDto> actual = webTestClient.get()
                .uri(URL_TEMPLATE + "/all")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
                .expectBody(new ParameterizedTypeReference<List<UserDetailsDto>>() {})
                .returnResult()
                .getResponseBody();

        assertThat(actual)
                .usingRecursiveComparison()
                .ignoringCollectionOrder()
                .isEqualTo(constantPayloadDtoList);
    }

    private RegistrationUserDetailsDto getRegistrationUserDetailsDto() {
        return RegistrationUserDetailsDto.builder()
                .email("john.doe@example.com")
                .password("password")
                .build();
    }

    private UserDetailsDto getUserDetailsDto() {
        return UserDetailsDto.builder()
                .userId("111")
                .username("john.doe")
                .isActive(true)
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .contactInfo(ContactInfoDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .email("john.doe@example.com")
                        .isEmailVerified(true)
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode(CountryPhoneCodeDto.builder()
                                        .value("+11")
                                        .key("+11")
                                        .build())
                                .phoneNumber("111-111-111")
                                .build())
                        .isPhoneVerified(true)
                        .birthdayDate(LocalDate.parse("1996-01-01"))
                        .build())
                .addresses(List.of
                        (UserAddressDto.builder()
                                .address(AddressDto.builder()
                                        .country(CountryDto.builder()
                                                .value("USA")
                                                .key("USA")
                                                .build())
                                        .city("city")
                                        .street("street")
                                        .buildingNumber("10")
                                        .florNumber(9)
                                        .apartmentNumber(201)
                                        .zipCode("90000")
                                        .build())
                                .isDefault(true)
                        .build()))
                .creditCards(List.of(
                        UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber("4444-1111-2222-3333")
                                        .monthOfExpire(6)
                                        .yearOfExpire(28)
                                        .isActive(true)
                                        .cvv("818")
                                        .ownerName("John")
                                        .ownerSecondName("Doe")
                                        .build())
                                .isDefault(true)
                        .build()))
                .devices(List.of(
                        DeviceDto.builder()
                                .ipAddress("ipAddress")
                                .loginTime(getLocalDateTime("2024-03-03 12:00:00"))
                                .userAgent("agent")
                                .screenWidth(1920)
                                .screenHeight(1080)
                                .timezone("timezone")
                                .language("language")
                                .platform("platform")
                                .isThisDevice(true)
                                .build()))
                .build();
    }

    private UserDetailsDto getUserDetailsDto2() {
        return UserDetailsDto.builder()
                .userId("123")
                .username("john.doe2")
                .isActive(true)
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .contactInfo(ContactInfoDto.builder()
                        .firstName("John2")
                        .lastName("Doe2")
                        .email("john.doe2@example.com")
                        .isEmailVerified(true)
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode(CountryPhoneCodeDto.builder()
                                        .value("+11")
                                        .key("+11")
                                        .build())
                                .phoneNumber("222-222-222")
                                .build())
                        .isPhoneVerified(true)
                        .birthdayDate(LocalDate.parse("1996-01-01"))
                        .build())
                .addresses(List.of
                        (UserAddressDto.builder()
                                .address(AddressDto.builder()
                                        .country(CountryDto.builder()
                                                .value("USA")
                                                .key("USA")
                                                .build())
                                        .city("city2")
                                        .street("street2")
                                        .buildingNumber("10")
                                        .florNumber(9)
                                        .apartmentNumber(101)
                                        .zipCode("91000")
                                        .build())
                                .isDefault(false)
                                .build()))
                .creditCards(List.of(
                        UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber("4444-1111-2222-2222")
                                        .monthOfExpire(9)
                                        .yearOfExpire(29)
                                        .isActive(true)
                                        .cvv("818")
                                        .ownerName("John2")
                                        .ownerSecondName("Doe2")
                                        .build())
                                .isDefault(false)
                                .build()))
                .devices(List.of(
                        DeviceDto.builder()
                                .ipAddress("ipAddress2")
                                .loginTime(getLocalDateTime("2024-03-03 12:00:00"))
                                .userAgent("agent2")
                                .screenWidth(1920)
                                .screenHeight(1080)
                                .timezone("timezone2")
                                .language("language2")
                                .platform("platform2")
                                .isThisDevice(true)
                                .build()))
                .build();
    }
    
    private UserDetailsDto getUpdateUserDetailsDtoMore() {
        return UserDetailsDto.builder()
                .userId("123")
                .username("Update john.doe2")
                .isActive(false)
                .contactInfo(ContactInfoDto.builder()
                        .firstName("Update John2")
                        .lastName("Update Doe2")
                        .email("Update john.doe2@example.com")
                        .isEmailVerified(false)
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode(CountryPhoneCodeDto.builder()
                                        .value("+11")
                                        .key("+11")
                                        .build())
                                .phoneNumber("333-333-333")
                                .build())
                        .isPhoneVerified(false)
                        .birthdayDate(LocalDate.parse("1997-01-01"))
                        .build())
                .addresses(List.of(
                        UserAddressDto.builder()
                                .address(AddressDto.builder()
                                        .country(CountryDto.builder()
                                                .value("USA")
                                                .key("USA")
                                                .build())
                                        .city("Update city2")
                                        .street("Update street2")
                                        .buildingNumber("10/2")
                                        .florNumber(99)
                                        .apartmentNumber(2011)
                                        .zipCode("Update 90000")
                                        .build())
                                .isDefault(false)
                                .build(),
                        UserAddressDto.builder()
                                .address(AddressDto.builder()
                                        .country(CountryDto.builder()
                                                .value("USA")
                                                .key("USA")
                                                .build())
                                        .city("New city3")
                                        .street("New street3")
                                        .buildingNumber("33")
                                        .florNumber(3)
                                        .apartmentNumber(333)
                                        .zipCode("New 90000")
                                        .build())
                                .isDefault(false)
                                .build()))
                .creditCards(List.of(
                        UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber("Update 4444-1111-2222-3333")
                                        .monthOfExpire(22)
                                        .yearOfExpire(22)
                                        .isActive(false)
                                        .cvv("Update 818")
                                        .ownerName("Update John")
                                        .ownerSecondName("Update Doe")
                                        .build())
                                .isDefault(false)
                                .build(),
                        UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber("New 4444-1111-2222-3333")
                                        .monthOfExpire(33)
                                        .yearOfExpire(33)
                                        .isActive(false)
                                        .cvv("New 818")
                                        .ownerName("New John")
                                        .ownerSecondName("New Doe")
                                        .build())
                                .isDefault(false)
                                .build()))
                .devices(List.of(
                        DeviceDto.builder()
                                .ipAddress("Update ipAddress")
                                .loginTime(getLocalDateTime("2025-03-03 12:00:00"))
                                .userAgent("Update agent")
                                .screenWidth(2000)
                                .screenHeight(1000)
                                .timezone("Update timezone")
                                .language("Update language")
                                .platform("Update platform")
                                .isThisDevice(false)
                                .build(),
                        DeviceDto.builder()
                                .ipAddress("New ipAddress")
                                .loginTime(getLocalDateTime("2025-03-03 12:00:00"))
                                .userAgent("New agent")
                                .screenWidth(2000)
                                .screenHeight(1000)
                                .timezone("New timezone")
                                .language("New language")
                                .platform("New platform")
                                .isThisDevice(false)
                                .build()))
                .build();
    }

    private UserDetailsDto getUpdateUserDetailsDtoLess() {
        return UserDetailsDto.builder()
                .userId("123")
                .username("Update john.doe2")
                .isActive(false)
                .contactInfo(ContactInfoDto.builder()
                        .firstName("Update John2")
                        .lastName("Update Doe2")
                        .email("Update john.doe2@example.com")
                        .isEmailVerified(false)
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode(CountryPhoneCodeDto.builder()
                                        .value("+11")
                                        .key("+11")
                                        .build())
                                .phoneNumber("333-333-333")
                                .build())
                        .isPhoneVerified(false)
                        .birthdayDate(LocalDate.parse("1997-01-01"))
                        .build())
                .addresses(List.of(
                        UserAddressDto.builder()
                                .address(AddressDto.builder()
                                        .country(CountryDto.builder()
                                                .value("USA")
                                                .key("USA")
                                                .build())
                                        .city("Update city2")
                                        .street("Update street2")
                                        .buildingNumber("10/2")
                                        .florNumber(99)
                                        .apartmentNumber(2011)
                                        .zipCode("Update 90000")
                                        .build())
                                .isDefault(false)
                                .build()))
                .creditCards(List.of(
                        UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber("Update 4444-1111-2222-3333")
                                        .monthOfExpire(22)
                                        .yearOfExpire(22)
                                        .isActive(false)
                                        .cvv("Update 818")
                                        .ownerName("Update John")
                                        .ownerSecondName("Update Doe")
                                        .build())
                                .isDefault(false)
                                .build()))
                .devices(List.of(
                        DeviceDto.builder()
                                .ipAddress("Update ipAddress")
                                .loginTime(getLocalDateTime("2025-03-03 12:00:00"))
                                .userAgent("Update agent")
                                .screenWidth(2000)
                                .screenHeight(1000)
                                .timezone("Update timezone")
                                .language("Update language")
                                .platform("Update platform")
                                .isThisDevice(false)
                                .build()))
                .build();
    }

    private UserDetailsStatusDto getUserDetailsStatusDto() {
        return UserDetailsStatusDto.builder()
                .userId("123")
                .status(false)
                .build();
    }

    private UserDetailsAddCollectionDto getNewUserDetailsAddressDto() {
        return UserDetailsAddCollectionDto.builder()
                .userId("123")
                .address(UserAddressDto.builder()
                        .address(AddressDto.builder()
                                .country(CountryDto.builder()
                                        .value("USA")
                                        .key("USA")
                                        .build())
                                .city("city3")
                                .street("street3")
                                .buildingNumber("33")
                                .florNumber(9)
                                .apartmentNumber(303)
                                .zipCode("91000")
                                .build())
                        .isDefault(true)
                        .build())
                .build();
    }

    private UserDetailsAddCollectionDto getExistingUserDetailsAddressDto() {
        return UserDetailsAddCollectionDto.builder()
                .userId("123")
                .address(UserAddressDto.builder()
                        .address(AddressDto.builder()
                                .country(CountryDto.builder()
                                        .value("USA")
                                        .key("USA")
                                        .build())
                                .city("city2")
                                .street("street2")
                                .buildingNumber("10")
                                .florNumber(9)
                                .apartmentNumber(101)
                                .zipCode("91000")
                                .build())
                        .isDefault(true)
                        .build())
                .build();
    }

    private UserDetailsAddCollectionDto getNewUserDetailsCreditCardDto() {
        return UserDetailsAddCollectionDto.builder()
                .userId("123")
                .creditCard(UserCreditCardDto.builder()
                        .creditCard(CreditCardDto.builder()
                                .cardNumber("4444-1111-2222-3333")
                                .monthOfExpire(22)
                                .yearOfExpire(22)
                                .isActive(false)
                                .cvv("818")
                                .ownerName("John2")
                                .ownerSecondName("Doe2")
                                .build())
                        .isDefault(true)
                        .build())
                .build();
    }

    private UserDetailsRemoveCollectionDto getUserDetailsRemoveAddressCollectionDto() {
        return UserDetailsRemoveCollectionDto.builder()
                .userId("123")
                .address(AddressDto.builder()
                        .country(CountryDto.builder()
                                .value("USA")
                                .key("USA")
                                .build())
                        .city("city2")
                        .street("street2")
                        .buildingNumber("10")
                        .florNumber(9)
                        .apartmentNumber(101)
                        .zipCode("91000")
                        .build())
                .build();
    }

    private UserDetailsRemoveCollectionDto getUserDetailsRemoveCreditCardCollectionDto() {
        return UserDetailsRemoveCollectionDto.builder()
                .userId("123")
                .creditCard(CreditCardDto.builder()
                        .cardNumber("4444-1111-2222-2222")
                        .monthOfExpire(9)
                        .yearOfExpire(29)
                        .isActive(false)
                        .cvv("818")
                        .ownerName("John2")
                        .ownerSecondName("Doe2")
                        .build())
                .build();
    }

    private UserDetailsAddCollectionDto getExistingUserDetailsCreditCardDto() {
        return UserDetailsAddCollectionDto.builder()
                .userId("123")
                .creditCard(UserCreditCardDto.builder()
                        .creditCard(CreditCardDto.builder()
                                .cardNumber("4444-1111-2222-2222")
                                .monthOfExpire(9)
                                .yearOfExpire(29)
                                .isActive(false)
                                .cvv("818")
                                .ownerName("John2")
                                .ownerSecondName("Doe2")
                                .build())
                        .isDefault(true)
                        .build())
                .build();
    }

    private UserDetailsAddCollectionDto getNewUserDetailsDeviceDto() {
        return UserDetailsAddCollectionDto.builder()
                .userId("123")
                .device(DeviceDto.builder()
                        .ipAddress("ipAddress3")
                        .loginTime(getLocalDateTime("2025-03-03 12:00:00"))
                        .userAgent("agent3")
                        .screenWidth(1920)
                        .screenHeight(1080)
                        .timezone("timezone3")
                        .language("language3")
                        .platform("platform3")
                        .build())
                .build();
    }

    private UserDetailsAddCollectionDto getExistingUserDetailsDeviceDto() {
        return UserDetailsAddCollectionDto.builder()
                .userId("123")
                .device(DeviceDto.builder()
                        .ipAddress("ipAddress2")
                        .loginTime(getLocalDateTime("2025-03-03 12:00:00"))
                        .userAgent("agent2")
                        .screenWidth(1920)
                        .screenHeight(1080)
                        .timezone("timezone2")
                        .language("language2")
                        .platform("platform2")
                        .build())
                .build();
    }

    private UserDetailsContactInfoDto getUpdateUserDetailsContactInfoDto() {
        return UserDetailsContactInfoDto.builder()
                .userId("123")
                .username("Update john.doe2")
                .isActive(false)
                .contactInfo(ContactInfoDto.builder()
                        .firstName("Update John2")
                        .lastName("Update Doe2")
                        .email("Update john.doe2@example.com")
                        .isEmailVerified(false)
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode(CountryPhoneCodeDto.builder()
                                        .value("+11")
                                        .key("+11")
                                        .build())
                                .phoneNumber("333-333-333")
                                .build())
                        .isPhoneVerified(false)
                        .birthdayDate(LocalDate.parse("1997-01-01"))
                        .build())
                .build();
    }

}
