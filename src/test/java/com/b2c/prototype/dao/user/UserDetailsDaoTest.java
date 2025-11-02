package com.b2c.prototype.dao.user;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.Device;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.util.CardUtil;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserDetailsDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/user/user_details/emptyUserDetailsDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE address RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE credit_card RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/dao/user/user_details/saveUserDetailsDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        UserDetails entity = getUserDetails();
        entity.setId(0L);
        entity.getContactInfo().setId(0L);
        entity.getContactInfo().getContactPhone().setId(0L);
        entity.getUserAddresses().forEach(userAddress -> {
            userAddress.setId(0L);
            userAddress.getAddress().setId(0L);
        });
        entity.getUserCreditCards().forEach(userCreditCard -> {
            userCreditCard.setId(0L);
            userCreditCard.getCreditCard().setId(0L);
        });
        entity.getDevices().forEach(device -> {
            device.setId(0L);
        });

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_details/testUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/user_details/updateUserDetailsDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        UserDetails entity = getUserDetails();
        entity.setUsername("Update username");
        entity.getContactInfo().setEmail("Update email");
        entity.getContactInfo().setFirstName("Update Wolter");
        entity.getContactInfo().setLastName("Update White");
        entity.getContactInfo().getContactPhone().setPhoneNumber("222-222-222");
        entity.getUserAddresses().forEach(userAddress -> {
            userAddress.getAddress().setStreet("Update street");
        });
        entity.getUserCreditCards().forEach(userCreditCard -> {
            userCreditCard.getCreditCard().setOwnerName("Update name");
            userCreditCard.getCreditCard().setOwnerSecondName("Update secondName");
        });
        entity.getDevices().forEach(device -> {
            device.setUserAgent("Update agent");
        });

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_details/testUserDetailsDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/user_details/emptyUserDetailsDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        UserDetails entity = getUserDetails();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_details/testUserDetailsDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        UserDetails expected = getUserDetails();

        Pair<String, Long> pair = Pair.of("id", 1L);
        UserDetails entity = generalEntityDao.findEntity("UserDetails.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_details/testUserDetailsDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        UserDetails expected = getUserDetails();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<UserDetails> optionEntity = generalEntityDao.findOptionEntity("UserDetails.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        UserDetails entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_details/testUserDetailsDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        UserDetails entity = getUserDetails();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<UserDetails> entityList = generalEntityDao.findEntityList("UserDetails.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private UserDetails getUserDetails() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .key("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        ContactInfo contactInfo = ContactInfo.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .email("email")
                .contactPhone(contactPhone)
                .isContactPhoneVerified(false)
                .isEmailVerified(false)
                .build();
        Country country = Country.builder()
                .id(1L)
                .key("USA")
                .value("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .city("city")
                .build();
        UserAddress userAddress = UserAddress.builder()
                .id(1L)
                .address(address)
                .isDefault(true)
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
                .id(1L)
                .creditCard(creditCard)
                .isDefault(false)
                .build();
        Device device = Device.builder()
                .id(1L)
                .userAgent("agent")
                .ipAddress("ipAddress")
                .language("language")
                .timezone("timezone")
                .platform("platform")
                .screenHeight(1080)
                .screenWidth(1920)
                .build();


        return UserDetails.builder()
                .id(1L)
                .username("username")
                .userId("123")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .isActive(true)
                .contactInfo(contactInfo)
                .userAddresses(Set.of(userAddress))
                .userCreditCards(Set.of(userCreditCard))
                .devices(Set.of(device))
                .build();
    }

}