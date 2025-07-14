package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.dao.user.BasicContactPhoneDao;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicContactPhoneDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ContactPhone.class, "contact_phone"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicContactPhoneDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/user/contact_phone/emptyContactPhoneDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
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
        return new EntityDataSet<>(contactPhone, "/datasets/user/contact_phone/testContactPhoneDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return new EntityDataSet<>(contactPhone, "/datasets/user/contact_phone/saveContactPhoneDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
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
        return new EntityDataSet<>(contactPhone, "/datasets/user/contact_phone/updateContactPhoneDataSet.yml");
    }
}