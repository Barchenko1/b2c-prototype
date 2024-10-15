package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserInfo;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.junit.jupiter.api.BeforeAll;

class BasicUserInfoDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(UserInfo.class, "user_info"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicUserInfoDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/user/user_info/emptyUserInfoDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .name("Wolter")
                .secondName("White")
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        return new EntityDataSet<>(userInfo, "/datasets/user/user_info/testUserInfoDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        UserInfo userInfo = UserInfo.builder()
                .name("Wolter")
                .secondName("White")
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        return new EntityDataSet<>(userInfo, "/datasets/user/user_info/saveUserInfoDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        UserInfo userInfo = UserInfo.builder()
                .id(1L)
                .name("Update Wolter")
                .secondName("Update White")
                .phoneNumber("111-111-111")
                .countryPhoneCode(countryPhoneCode)
                .build();
        return new EntityDataSet<>(userInfo, "/datasets/user/user_info/updateUserInfoDataSet.yml");
    }
}