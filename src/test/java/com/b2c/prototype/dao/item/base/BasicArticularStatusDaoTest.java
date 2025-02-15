package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicArticularStatusDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ArticularStatus.class, "articular_status"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicItemStatusDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/articular_status/emptyArticularStatusDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .value("Test")
                .label("Test")
                .build();
        return new EntityDataSet<>(articularStatus, "/datasets/item/articular_status/testArticularStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ArticularStatus articularStatus = ArticularStatus.builder()
                .value("New")
                .label("New")
                .build();
        return new EntityDataSet<>(articularStatus, "/datasets/item/articular_status/saveArticularStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .value("Update Test")
                .label("Test")
                .build();
        return new EntityDataSet<>(articularStatus, "/datasets/item/articular_status/updateArticularStatusDataSet.yml");
    }

}