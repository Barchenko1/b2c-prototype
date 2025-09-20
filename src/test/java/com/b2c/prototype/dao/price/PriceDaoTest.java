package com.b2c.prototype.dao.price;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceDaoTest extends AbstractDaoTest {
    
    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/price/price/emptyPriceDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE price RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE currency RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/dao/price/price/savePriceDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Price entity = getPrice();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/price/testPriceDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/price/updatePriceDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Price entity = getPrice();
        entity.setAmount(20);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/price/emptyPriceCurrencyDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/price/savePriceDataSet.yml", orderBy = "id")
    public void mergeEntity2_success() {
        Price entity = getPrice();
        entity.setId(0);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/price/testPriceDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/price/emptyPriceCurrencyDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Price entity = getPrice();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/price/testPriceDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Price expected = getPrice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Price entity = generalEntityDao.findEntity("Price.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/price/testPriceDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Price expected = getPrice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Price> optionEntity = generalEntityDao.findOptionEntity("Price.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Price entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/price/testPriceDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Price entity = getPrice();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Price> entityList = generalEntityDao.findEntityList("Price.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Price getPrice() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();

        return Price.builder()
                .id(1L)
                .amount(10)
                .currency(currency)
                .build();
    }
}