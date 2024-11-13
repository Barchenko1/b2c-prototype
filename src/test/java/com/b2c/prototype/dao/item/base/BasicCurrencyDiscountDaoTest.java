package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.price.Currency;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCurrencyDiscountDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(CurrencyDiscount.class, "currency_discount"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicCurrencyDiscountDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/currency_discount/emptyCurrencyDiscountDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(10)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        return new EntityDataSet<>(currencyDiscount, "/datasets/item/currency_discount/testCurrencyDiscountDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .amount(10)
                .currency(currency)
                .charSequenceCode("abc")
                .build();
        return new EntityDataSet<>(currencyDiscount, "/datasets/item/currency_discount/saveCurrencyDiscountDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        return new EntityDataSet<>(currencyDiscount, "/datasets/item/currency_discount/updateCurrencyDiscountDataSet.yml");
    }
}