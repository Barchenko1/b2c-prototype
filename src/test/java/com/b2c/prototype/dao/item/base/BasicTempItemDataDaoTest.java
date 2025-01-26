package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractTempEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.embedded.item.TempItemData;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicTempItemDataDaoTest extends AbstractTempEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(TempItemData.class, "temp_item_data"));
        entityIdentifierDao = new EntityIdentifierDao(entityMappingManager);
        dao = new BasicTempItemDataDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/temp_item_data/emptyTempItemDataDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        TempItemData tempItemData = TempItemData.builder()
                .id(1L)
                .articularId("100a")
                .build();
        return new EntityDataSet<>(tempItemData, "/datasets/item/temp_item_data/testTempItemDataDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        TempItemData tempItemData = TempItemData.builder()
                .articularId("100a")
                .build();
        return new EntityDataSet<>(tempItemData, "/datasets/item/temp_item_data/saveTempItemDataDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        TempItemData tempItemData = TempItemData.builder()
                .id(1L)
                .articularId("100b")
                .build();
        return new EntityDataSet<>(tempItemData, "/datasets/item/temp_item_data/updateTempItemDataDataSet.yml");
    }
}