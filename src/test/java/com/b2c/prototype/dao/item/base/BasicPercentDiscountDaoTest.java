package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicPercentDiscountDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(PercentDiscount.class, "percent_discount"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicPercentDiscountDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/percent_discount/emptyPercentDiscountDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        PercentDiscount percentDiscount = PercentDiscount.builder()
                .id(1L)
                .amount(10)
                .charSequenceCode("abc")
                .build();
        return new EntityDataSet<>(percentDiscount, "/datasets/item/percent_discount/testPercentDiscountDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        PercentDiscount percentDiscount = PercentDiscount.builder()
                .amount(10)
                .charSequenceCode("abc")
                .build();
        return new EntityDataSet<>(percentDiscount, "/datasets/item/percent_discount/savePercentDiscountDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        PercentDiscount percentDiscount = PercentDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .build();
        return new EntityDataSet<>(percentDiscount, "/datasets/item/percent_discount/updatePercentDiscountDataSet.yml");
    }
}