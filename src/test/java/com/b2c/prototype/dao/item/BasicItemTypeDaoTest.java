package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicItemTypeDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemType.class, "item_type"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicItemTypeDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/item_type/emptyItemTypeDataSet.yml";
    }

    @Override
    protected EntityDataSet<ItemType> getSaveDataSet() {
        ItemType itemType = ItemType.builder()
                .value("Clothes")
                .label("Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/saveItemTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<ItemType> getTestDataSet() {
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .label("Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/testItemTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<ItemType> getUpdateDataSet() {
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Update Clothes")
                .label("Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/updateItemTypeDataSet.yml");
    }

}