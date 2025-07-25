package com.b2c.prototype.dao.option;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.b2c.prototype.modal.entity.option.ZoneOption;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import com.tm.core.process.dao.query.QueryService;
import org.junit.jupiter.api.BeforeAll;

class BasicZoneOptionDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ZoneOption.class, "zone_option"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicZoneOptionDao(sessionFactory, queryService);
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