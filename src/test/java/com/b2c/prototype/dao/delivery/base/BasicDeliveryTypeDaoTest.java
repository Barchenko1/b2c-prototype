package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicDeliveryTypeDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(DeliveryType.class, "delivery_type"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicDeliveryTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/delivery/delivery_type/emptyDeliveryTypeDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .name("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/delivery_type/testDeliveryTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .name("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/delivery_type/saveDeliveryTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .name("Update Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/delivery_type/updateDeliveryTypeDataSet.yml");
    }

}