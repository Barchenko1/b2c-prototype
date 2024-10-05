package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import org.junit.jupiter.api.BeforeAll;

class BasicItemStatusDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
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
                .name("Test")
                .build();
        return new EntityDataSet<>(itemStatus, "/datasets/item/item_status/testItemStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ItemStatus itemStatus = ItemStatus.builder()
                .name("New")
                .build();
        return new EntityDataSet<>(itemStatus, "/datasets/item/item_status/saveItemStatusDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .name("Update Test")
                .build();
        return new EntityDataSet<>(itemStatus, "/datasets/item/item_status/updateItemStatusDataSet.yml");
    }

}