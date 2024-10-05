package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.b2c.prototype.modal.entity.user.UserInfo;
import org.junit.jupiter.api.BeforeAll;

class BasicUserInfoDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
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