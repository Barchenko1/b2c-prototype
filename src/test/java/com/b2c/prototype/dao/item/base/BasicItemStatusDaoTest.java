package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicItemStatusDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemStatus.class, "item_status"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicItemStatusDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/item_status/emptyItemStatusDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .value("Test")
                .build();
        return new EntityDataSet<>(itemStatus, "/datasets/item/item_status/testItemStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ItemStatus itemStatus = ItemStatus.builder()
                .value("New")
                .build();
        return new EntityDataSet<>(itemStatus, "/datasets/item/item_status/saveItemStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .value("Update Test")
                .build();
        return new EntityDataSet<>(itemStatus, "/datasets/item/item_status/updateItemStatusDataSet.yml");
    }

}