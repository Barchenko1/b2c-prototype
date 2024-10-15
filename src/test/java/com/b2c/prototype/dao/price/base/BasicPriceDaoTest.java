package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.junit.jupiter.api.BeforeAll;

class BasicPriceDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Price.class, "price"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicPriceDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/price/price/emptyPriceDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .name("USD")
                .build();

        Price price = Price.builder()
                .id(1L)
                .amount(10)
                .currency(currency)
                .build();
        return new EntityDataSet<>(price, "/datasets/price/price/testPriceDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .name("USD")
                .build();

        Price price = Price.builder()
                .amount(10)
                .currency(currency)
                .build();
        return new EntityDataSet<>(price, "/datasets/price/price/savePriceDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .name("USD")
                .build();

        Price price = Price.builder()
                .id(1L)
                .amount(20)
                .currency(currency)
                .build();
        return new EntityDataSet<>(price, "/datasets/price/price/updatePriceDataSet.yml");
    }
}