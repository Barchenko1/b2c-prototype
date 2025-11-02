package com.b2c.prototype.dao.delivery;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DeliveryDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery/emptyDeliveryDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE address RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE credit_card RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/dao/delivery/delivery/saveDeliveryDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Delivery entity = getDelivery();
        entity.setId(0L);
        entity.getAddress().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery/testDeliveryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/delivery/updateDeliveryDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Delivery entity = getDelivery();
        entity.getAddress().setStreet("Update street");
        entity.getAddress().setZipCode("90001");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery/testDeliveryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/delivery/emptyDeliveryDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Delivery entity = getDelivery();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery/testDeliveryDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Delivery expected = getDelivery();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Delivery entity = generalEntityDao.findEntity("Delivery.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery/testDeliveryDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Delivery expected = getDelivery();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Delivery> optionEntity = generalEntityDao.findOptionEntity("Delivery.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Delivery entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery/testDeliveryDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Delivery entity = getDelivery();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Delivery> entityList = generalEntityDao.findEntityList("Delivery.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Delivery getDelivery() {
        Country country = Country.builder()
                .id(1L)
                .key("USA")
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
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Type")
                .key("Type")
                .build();

        return Delivery.builder()
                .id(1L)
                .address(address)
                .deliveryType(deliveryType)
                .build();
    }

}
