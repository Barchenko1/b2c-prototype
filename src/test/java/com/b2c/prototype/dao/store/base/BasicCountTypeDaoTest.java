package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.AbstractSimpleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.store.CountType;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCountTypeDaoTest extends AbstractSimpleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(CountType.class, "count_type"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicCountTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/store/count_type/emptyCountTypeDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        CountType countType = CountType.builder()
                .id(1L)
                .value("LIMITED")
                .label("limited")
                .build();
        return new EntityDataSet<>(countType, "/datasets/store/count_type/testCountTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CountType countType = CountType.builder()
                .value("LIMITED")
                .label("limited")
                .build();
        return new EntityDataSet<>(countType, "/datasets/store/count_type/saveCountTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        CountType countType = CountType.builder()
                .id(1L)
                .value("UNLIMITED")
                .label("unlimited")
                .build();
        return new EntityDataSet<>(countType, "/datasets/store/count_type/updateCountTypeDataSet.yml");
    }
}