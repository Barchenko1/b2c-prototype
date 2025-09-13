package com.b2c.prototype.dao.address;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AddressDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/order/address/emptyAddressDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/order/address/saveAddressDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Address entity = getAddress();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/order/address/testAddressDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/order/address/updateAddressDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Address entity = getAddress();
        entity.setCity("new city");
        entity.setStreet("Update street");
        entity.setApartmentNumber(102);
        entity.setZipCode("90001");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/order/address/testAddressDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/order/address/emptyAddressDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Address entity = getAddress();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/order/address/testAddressDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Address expected = getAddress();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Address entity = generalEntityDao.findEntity("Address.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/order/address/testAddressDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Address expected = getAddress();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Address> optionEntity = generalEntityDao.findOptionEntity("Address.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Address entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/order/address/testAddressDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Address expected = getAddress();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Address> entityList = generalEntityDao.findEntityList("Address.findById", List.of(pair));

        assertEquals(List.of(expected), entityList);
    }

    private Address getAddress() {
        Country country = Country.builder()
                .id(1L)
                .label("USA")
                .value("USA")
                .build();

        return Address.builder()
                .id(1L)
                .city("city")
                .country(country)
                .street("street")
                .buildingNumber("1")
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
    }

}