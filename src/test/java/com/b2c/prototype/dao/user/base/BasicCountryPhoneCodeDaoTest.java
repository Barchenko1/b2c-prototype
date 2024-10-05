package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import org.junit.jupiter.api.BeforeAll;

class BasicCountryPhoneCodeDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        dao = new BasicCountryPhoneCodeDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/user/country_phone_code/emptyCountryPhoneCodeDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+48")
                .build();
        return new EntityDataSet<>(countryPhoneCode, "/datasets/user/country_phone_code/testCountryPhoneCodeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .code("+48")
                .build();
        return new EntityDataSet<>(countryPhoneCode, "/datasets/user/country_phone_code/saveCountryPhoneCodeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("Update +48")
                .build();
        return new EntityDataSet<>(countryPhoneCode, "/datasets/user/country_phone_code/updateCountryPhoneCodeDataSet.yml");
    }

}