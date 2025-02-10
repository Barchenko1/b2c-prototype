package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.store.CountType;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCountTypeDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(CountType.class, "count_type"));
        entityIdentifierDao = new EntityIdentifierDao(entityMappingManager);
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