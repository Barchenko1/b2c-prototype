package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import com.tm.core.process.dao.identifier.QueryService;
import org.junit.jupiter.api.BeforeAll;

class BasicTimeDurationOptionDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(TimeDurationOption.class, "time_duration_option"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicTimeDurationOptionDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/delivery/time_duration_option/emptyTimeDurationOption.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Post")
                .label("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/time_duration_option/testTimeDurationOption.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .value("Post")
                .label("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/time_duration_option/saveTimeDurationOption.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Update Post")
                .label("Post")
                .build();
        return new EntityDataSet<>(deliveryType, "/datasets/delivery/time_duration_option/updateTimeDurationOption.yml");
    }
}