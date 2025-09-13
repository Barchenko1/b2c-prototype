package com.b2c.prototype.dao.user;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ContactInfoDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/user/contact_info/emptyContactInfoDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/user/contact_info/saveContactInfoDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ContactInfo entity = getContactInfo();
        entity.setId(0L);
        entity.getContactPhone().setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/contact_info/testContactInfoDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/user/contact_info/updateContactInfoDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ContactInfo entity = getContactInfo();
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(2L)
                .value("+22")
                .label("+22")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("222-222-222")
                .build();
        entity.setContactPhone(contactPhone);
        entity.setFirstName("Update Wolter");
        entity.setLastName("Update White");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/contact_info/testContactInfoDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/user/contact_info/emptyContactInfoDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ContactInfo entity = getContactInfo();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/user/contact_info/testContactInfoDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ContactInfo expected = getContactInfo();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ContactInfo entity = generalEntityDao.findEntity("ContactInfo.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/user/contact_info/testContactInfoDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ContactInfo expected = getContactInfo();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ContactInfo> optionEntity = generalEntityDao.findOptionEntity("ContactInfo.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ContactInfo entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/user/contact_info/testContactInfoDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ContactInfo entity = getContactInfo();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ContactInfo> entityList = generalEntityDao.findEntityList("ContactInfo.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private ContactInfo getContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return ContactInfo.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .email("email")
                .birthdayDate(getDate("2024-03-03"))
                .contactPhone(contactPhone)
                .isEmailVerified(false)
                .isContactPhoneVerified(false)
                .build();
    }

    private Date getDate(String date) {
        ZoneId zone = ZoneId.systemDefault();
        return Date.from(
                LocalDate.parse(date).atStartOfDay(zone).toInstant());
    }

}