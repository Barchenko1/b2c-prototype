package com.b2c.prototype.dao.delivery;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
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

class DeliveryTypeDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery_type/emptyDeliveryTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/delivery_type/saveDeliveryTypeDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        DeliveryType entity = getDeliveryType();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery_type/testDeliveryTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/delivery_type/updateDeliveryTypeDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        DeliveryType entity = getDeliveryType();
        entity.setKey("Update Post");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery_type/testDeliveryTypeDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/delivery_type/emptyDeliveryTypeDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        DeliveryType entity = getDeliveryType();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery_type/testDeliveryTypeDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        DeliveryType expected = getDeliveryType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        DeliveryType entity = generalEntityDao.findEntity("DeliveryType.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery_type/testDeliveryTypeDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        DeliveryType expected = getDeliveryType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<DeliveryType> optionEntity = generalEntityDao.findOptionEntity("DeliveryType.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        DeliveryType entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/delivery_type/testDeliveryTypeDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        DeliveryType entity = getDeliveryType();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<DeliveryType> entityList = generalEntityDao.findEntityList("DeliveryType.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private DeliveryType getDeliveryType() {
        return DeliveryType.builder()
                .id(1L)
                .key("Post")
                .value("Post")
                .build();

    }

}