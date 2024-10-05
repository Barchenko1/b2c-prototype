package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.ItemType;
import org.junit.jupiter.api.BeforeAll;

class BasicItemTypeDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        dao = new BasicItemTypeDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/item_type/emptyItemTypeDataSet.yml";
    }

    @Override
    protected EntityDataSet<ItemType> getSaveDataSet() {
        ItemType itemType = ItemType.builder()
                .name("Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/saveItemTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<ItemType> getTestDataSet() {
        ItemType itemType = ItemType.builder()
                .id(1L)
                .name("Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/testItemTypeDataSet.yml");
    }

    @Override
    protected EntityDataSet<ItemType> getUpdateDataSet() {
        ItemType itemType = ItemType.builder()
                .id(1L)
                .name("Update Clothes")
                .build();
        return new EntityDataSet<>(itemType, "/datasets/item/item_type/updateItemTypeDataSet.yml");
    }

}