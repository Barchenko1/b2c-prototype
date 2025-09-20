package com.b2c.prototype.dao.user;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
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

class CountryPhoneCodeDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/user/country_phone_code/emptyCountryPhoneCodeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/country_phone_code/saveCountryPhoneCodeDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        CountryPhoneCode entity = getCountryPhoneCode();
        entity.setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/country_phone_code/testCountryPhoneCodeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/country_phone_code/updateCountryPhoneCodeDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        CountryPhoneCode entity = getCountryPhoneCode();
        entity.setLabel("Update +11");
        entity.setValue("Update +11");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/country_phone_code/testCountryPhoneCodeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/country_phone_code/emptyCountryPhoneCodeDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        CountryPhoneCode entity = getCountryPhoneCode();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/country_phone_code/testCountryPhoneCodeDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        CountryPhoneCode expected = getCountryPhoneCode();

        Pair<String, Long> pair = Pair.of("id", 1L);
        CountryPhoneCode entity = generalEntityDao.findEntity("CountryPhoneCode.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/country_phone_code/testCountryPhoneCodeDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        CountryPhoneCode expected = getCountryPhoneCode();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<CountryPhoneCode> optionEntity = generalEntityDao.findOptionEntity("CountryPhoneCode.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        CountryPhoneCode entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/country_phone_code/testCountryPhoneCodeDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        CountryPhoneCode entity = getCountryPhoneCode();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<CountryPhoneCode> entityList = generalEntityDao.findEntityList("CountryPhoneCode.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private CountryPhoneCode getCountryPhoneCode() {
        return CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
    }

}