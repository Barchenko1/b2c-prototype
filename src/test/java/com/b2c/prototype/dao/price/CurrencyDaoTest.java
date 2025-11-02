package com.b2c.prototype.dao.price;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.price.Currency;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrencyDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/price/currency/emptyCurrencyDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/currency/saveCurrencyDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Currency entity = getCurrency();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency/testCurrencyDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/currency/updateCurrencyDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Currency entity = getCurrency();
        entity.setKey("Update USD");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency/testCurrencyDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/currency/emptyCurrencyDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Currency entity = getCurrency();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency/testCurrencyDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Currency expected = getCurrency();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Currency entity = generalEntityDao.findEntity("Currency.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency/testCurrencyDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Currency expected = getCurrency();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Currency> optionEntity = generalEntityDao.findOptionEntity("Currency.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Currency entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency/testCurrencyDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Currency entity = getCurrency();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Currency> entityList = generalEntityDao.findEntityList("Currency.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private Currency getCurrency() {
        return Currency.builder()
                .id(1L)
                .key("USD")
                .value("USD")
                .build();
    }
}
