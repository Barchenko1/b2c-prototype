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
import com.b2c.prototype.modal.dto.payload.user.UserDetailsDto;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;

import java.time.LocalDate;

import java.util.List;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserDetailsControllerE2ETest extends BasicE2ETest {

    private static final String URL_TEMPLATE = "/api/v1/user/details";

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {

    })
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testE2ERegistrationUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"user_id", "date_of_create"})
    void testCreateNewUserDetails() {
        RegistrationUserDetailsDto dto = getRegistrationUserDetailsDto();
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
    @DataSet(value = "datasets/e2e/user/user_details/testUpdateE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPutUserDetailsMore() {
        UserDetailsDto dto = getUpdateUserDetailsDto1();
        String jsonPayload = writeValueAsString(dto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testUpdateE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPatchUserDetailsMore() {
        UserDetailsDto dto = getUpdateUserDetailsDto1();
        String jsonPayload = writeValueAsString(dto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPutUserDetailsLess() {
        UserDetailsDto dto = getUpdateUserDetailsDto1();
        String jsonPayload = writeValueAsString(dto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPatchUserDetailsLess() {
        UserDetailsDto dto = getUpdateUserDetailsDto1();
        String jsonPayload = writeValueAsString(dto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testE2ERegistrationUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"user_id", "date_of_create"})
    void testAddNewUserCard() {
        UserCreditCardDto dto = UserCreditCardDto.builder().build();
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
    @DataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testE2ERegistrationUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"user_id", "date_of_create"})
    void testDeleteUserCard() {
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
    @DataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testE2ERegistrationUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"user_id", "date_of_create"})
    void testAddNewUserAddress() {
        RegistrationUserDetailsDto dto = getRegistrationUserDetailsDto();
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
    @DataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testE2ERegistrationUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"user_id", "date_of_create"})
    void testDeleteUserAddress() {
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
    @DataSet(value = "datasets/e2e/user/user_details/emptyE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/testE2ERegistrationUserDetailsDataSet.yml", orderBy = {"id"},
            ignoreCols = {"user_id", "date_of_create"})
    void testAddNewUserDevice() {
        UserCreditCardDto dto = null;
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
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPatchUserDetailsStatus() {
        UserDetailsDto dto = getUserDetailsDto();
        String jsonPayload = writeValueAsString(dto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPatchUserDetailsVerifyEmail() {
        UserDetailsDto dto = getUserDetailsDto();
        String jsonPayload = writeValueAsString(dto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(jsonPayload)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/e2e/user/user_details/updateMoreE2EUserDetailsDataSet.yml", orderBy = "id")
    void testPatchUserDetailsVerifyPhone() {
        UserDetailsDto dto = getUserDetailsDto();
        String jsonPayload = writeValueAsString(dto);
        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path(URL_TEMPLATE)
                        .queryParam("userId", "123")
                        .build())
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
                        .queryParam("userId", "1234")
                        .build())
                .accept(MediaType.TEXT_PLAIN)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    @DataSet(value = "datasets/e2e/user/user_details/testE2EUserDetailsDataSet.yml", cleanBefore = true)
    void testGetUserDetails() {
        UserDetailsDto dto = getUserDetailsDto();

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
                .userId("123")
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
                                        .label("+11")
                                        .value("+11")
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
                                                .label("USA")
                                                .value("USA")
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
                .userId("1234")
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
                                        .label("+11")
                                        .value("+11")
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
                                                .label("USA")
                                                .value("USA")
                                                .build())
                                        .city("city2")
                                        .street("street2")
                                        .buildingNumber("10")
                                        .florNumber(9)
                                        .apartmentNumber(101)
                                        .zipCode("91000")
                                        .build())
                                .isDefault(true)
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
                                .isDefault(true)
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
    
    private UserDetailsDto getUpdateUserDetailsDto1() {
        return UserDetailsDto.builder()
                .userId("Update 123")
                .username("Update john.doe")
                .isActive(false)
                .dateOfCreate(getLocalDateTime("2025-03-03 12:00:00"))
                .contactInfo(ContactInfoDto.builder()
                        .firstName("Update John")
                        .lastName("Update Doe")
                        .email("Update john.doe@example.com")
                        .isEmailVerified(false)
                        .contactPhone(ContactPhoneDto.builder()
                                .countryPhoneCode(CountryPhoneCodeDto.builder()
                                        .label("+11")
                                        .value("+11")
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
                                                .label("USA")
                                                .value("USA")
                                                .build())
                                        .city("Update city")
                                        .street("Update street")
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
                                                .label("USA")
                                                .value("USA")
                                                .build())
                                        .city("New city")
                                        .street("New street")
                                        .buildingNumber("10/2")
                                        .florNumber(99)
                                        .apartmentNumber(2011)
                                        .zipCode("New 90000")
                                        .build())
                                .isDefault(false)
                                .build()))
                .creditCards(List.of(
                        UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber("3333-3333-3333-3333")
                                        .monthOfExpire(3)
                                        .yearOfExpire(30)
                                        .isActive(false)
                                        .cvv("Update 818")
                                        .ownerName("Update John")
                                        .ownerSecondName("Update Doe")
                                        .build())
                                .isDefault(false)
                                .build(),
                        UserCreditCardDto.builder()
                                .creditCard(CreditCardDto.builder()
                                        .cardNumber("3333-3333-3333-3333")
                                        .monthOfExpire(3)
                                        .yearOfExpire(30)
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

}
