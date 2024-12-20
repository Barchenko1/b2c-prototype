package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicItemTypeDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemType.class, "item_type"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicItemTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/item_type/emptyItemTypeDataSet.yml";
    }

    @Override
    protected EntityDataSet<ItemType> getSaveDataSet() {
        ItemType itemType = ItemType.builder()
                .value("Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/saveItemTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<ItemType> getTestDataSet() {
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/testItemTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<ItemType> getUpdateDataSet() {
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Update Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/updateItemTypeDataSet.yml");
    }

}