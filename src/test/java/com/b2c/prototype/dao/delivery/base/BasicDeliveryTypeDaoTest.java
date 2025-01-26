package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicDeliveryTypeDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(DeliveryType.class, "delivery_type"));
        entityIdentifierDao = new EntityIdentifierDao(entityMappingManager);
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
                .value("Post")
                .label("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/delivery_type/testDeliveryTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .value("Post")
                .label("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/delivery_type/saveDeliveryTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Update Post")
                .label("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/delivery_type/updateDeliveryTypeDataSet.yml");
    }

}