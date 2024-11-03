package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractSingleEntityCacheDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.embedded.user.TempUserProfile;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicTempUserProfileDaoTest extends AbstractSingleEntityCacheDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(TempUserProfile.class, "temp_user_profile"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicTempUserProfileDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/user/temp_user_profile/emptyTempUserProfileDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        TempUserProfile tempUserProfile = TempUserProfile.builder()
                .id(1L)
                .sessionId("abc")
                .email("abc@gmail.com")
                .username("abc")
                .build();
        return new EntityDataSet<>(tempUserProfile, "/datasets/user/temp_user_profile/testTempUserProfileDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        TempUserProfile tempUserProfile = TempUserProfile.builder()
                .sessionId("abc")
                .email("abc@mail.com")
                .username("abc")
                .build();
        return new EntityDataSet<>(tempUserProfile, "/datasets/user/temp_user_profile/saveTempUserProfileDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        TempUserProfile tempUserProfile = TempUserProfile.builder()
                .id(1L)
                .sessionId("bbb")
                .email("bbb@mail.com")
                .username("bbb")
                .build();
        return new EntityDataSet<>(tempUserProfile, "/datasets/user/temp_user_profile/updateTempUserProfileDataSet.yml");
    }
}