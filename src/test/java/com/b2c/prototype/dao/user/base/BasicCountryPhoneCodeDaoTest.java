package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCountryPhoneCodeDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(CountryPhoneCode.class, "country_phone_code"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicCountryPhoneCodeDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/user/country_phone_code/emptyCountryPhoneCodeDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+48")
                .label("+48")
                .build();
        return new EntityDataSet<>(countryPhoneCode, "/datasets/user/country_phone_code/testCountryPhoneCodeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .value("+48")
                .label("+48")
                .build();
        return new EntityDataSet<>(countryPhoneCode, "/datasets/user/country_phone_code/saveCountryPhoneCodeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("Update +48")
                .label("Update +48")
                .build();
        return new EntityDataSet<>(countryPhoneCode, "/datasets/user/country_phone_code/updateCountryPhoneCodeDataSet.yml");
    }

}