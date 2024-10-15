package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.junit.jupiter.api.BeforeAll;

class BasicCountryPhoneCodeDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(CountryPhoneCode.class, "country_phone_code"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
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