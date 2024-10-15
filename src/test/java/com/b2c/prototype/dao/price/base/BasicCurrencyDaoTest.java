package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.price.Currency;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.junit.jupiter.api.BeforeAll;

class BasicCurrencyDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Currency.class, "currency"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicCurrencyDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/price/currency/emptyCurrencyDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .name("USD")
                .build();
        return new EntityDataSet<>(currency, "/datasets/price/currency/testCurrencyDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Currency currency = Currency.builder()
                .name("USD")
                .build();
        return new EntityDataSet<>(currency, "/datasets/price/currency/saveCurrencyDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .name("Update USD")
                .build();
        return new EntityDataSet<>(currency, "/datasets/price/currency/updateCurrencyDataSet.yml");
    }
}