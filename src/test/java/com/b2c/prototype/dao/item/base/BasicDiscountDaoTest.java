package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.price.Currency;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicDiscountDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Discount.class, "discount"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicDiscountDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/discount/emptyDiscountDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(10)
                .charSequenceCode("abc")
                .isPercent(false)
                .isActive(true)
                .currency(currency)
                .build();
        return new EntityDataSet<>(discount, "/datasets/item/discount/testDiscountDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .amount(10)
                .currency(currency)
                .isPercent(false)
                .isActive(true)
                .charSequenceCode("abc")
                .build();
        return new EntityDataSet<>(discount, "/datasets/item/discount/saveDiscountDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .isPercent(false)
                .isActive(true)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        return new EntityDataSet<>(discount, "/datasets/item/discount/updateDiscountDataSet.yml");
    }

}