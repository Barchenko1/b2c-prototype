package com.b2c.prototype.dao.price.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicPriceDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Price.class, "price"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicPriceDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/price/price/emptyPriceDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
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
                .label("USD")
                .value("USD")
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
                .label("USD")
                .value("USD")
                .build();

        Price price = Price.builder()
                .id(1L)
                .amount(20)
                .currency(currency)
                .build();
        return new EntityDataSet<>(price, "/datasets/price/price/updatePriceDataSet.yml");
    }
}