package com.b2c.prototype.dao.user;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ContactPhoneDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/user/contact_phone/emptyContactPhoneDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/contact_phone/saveContactPhoneDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ContactPhone entity = getContactPhone();
        entity.setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/contact_phone/testContactPhoneDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/contact_phone/updateContactPhoneDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ContactPhone entity = getContactPhone();
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(2L)
                .key("+22")
                .value("+22")
                .build();
        entity.setCountryPhoneCode(countryPhoneCode);
        entity.setPhoneNumber("222-222-222");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/contact_phone/testContactPhoneDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/contact_phone/emptyContactPhoneDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ContactPhone entity = getContactPhone();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/contact_phone/testContactPhoneDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ContactPhone expected = getContactPhone();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ContactPhone entity = generalEntityDao.findEntity("ContactPhone.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/contact_phone/testContactPhoneDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ContactPhone expected = getContactPhone();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ContactPhone> optionEntity = generalEntityDao.findOptionEntity("ContactPhone.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ContactPhone entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/contact_phone/testContactPhoneDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ContactPhone entity = getContactPhone();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ContactPhone> entityList = generalEntityDao.findEntityList("ContactPhone.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private ContactPhone getContactPhone() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .key("+11")
                .build();
        return ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
    }

}