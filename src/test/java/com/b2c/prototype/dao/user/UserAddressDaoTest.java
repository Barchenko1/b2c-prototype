package com.b2c.prototype.dao.user;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.address.UserAddress;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserAddressDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/user/user_address/emptyUserAddressDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE address RESTART IDENTITY CASCADE"
            }
    )
    @ExpectedDataSet(value = "datasets/dao/user/user_address/saveUserAddressDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        UserAddress entity = getUserAddress();
        entity.setId(0L);
        entity.getAddress().setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_address/testUserAddressDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/user_address/updateUserAddressDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        UserAddress entity = getUserAddress();
        entity.getAddress().setStreet("Update street");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_address/testUserAddressDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/user/user_address/emptyUserAddressDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        UserAddress entity = getUserAddress();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_address/testUserAddressDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        UserAddress expected = getUserAddress();

        Pair<String, Long> pair = Pair.of("id", 1L);
        UserAddress entity = generalEntityDao.findEntity("UserAddress.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_address/testUserAddressDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        UserAddress expected = getUserAddress();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<UserAddress> optionEntity = generalEntityDao.findOptionEntity("UserAddress.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        UserAddress entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/user/user_address/testUserAddressDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        UserAddress entity = getUserAddress();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<UserAddress> entityList = generalEntityDao.findEntityList("UserAddress.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private UserAddress getUserAddress() {
        Country country = Country.builder()
                .id(1L)
                .label("USA")
                .value("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .city("city")
                .build();
        return UserAddress.builder()
                .id(1L)
                .address(address)
                .isDefault(true)
                .build();
    }

}