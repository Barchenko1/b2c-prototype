package com.b2c.prototype.dao.price;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.b2c.prototype.modal.entity.price.Currency;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CurrencyCoefficientDaoTest extends AbstractDaoTest {
    
    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/price/currency_coefficient/emptyCurrencyCoefficientDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/currency_coefficient/saveCurrencyCoefficientDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        CurrencyCoefficient entity = getCurrencyCoefficient();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency_coefficient/testCurrencyCoefficientDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/currency_coefficient/updateCurrencyCoefficientDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        CurrencyCoefficient entity = getCurrencyCoefficient();
        entity.setCoefficient(0.97);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency_coefficient/testCurrencyCoefficientDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/currency_coefficient/emptyCurrencyCoefficientDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        CurrencyCoefficient entity = getCurrencyCoefficient();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency_coefficient/testCurrencyCoefficientDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        CurrencyCoefficient expected = getCurrencyCoefficient();

        Pair<String, Long> pair = Pair.of("id", 1L);
        CurrencyCoefficient entity = generalEntityDao.findEntity("CurrencyCoefficient.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency_coefficient/testCurrencyCoefficientDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        CurrencyCoefficient expected = getCurrencyCoefficient();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<CurrencyCoefficient> optionEntity = generalEntityDao.findOptionEntity("CurrencyCoefficient.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        CurrencyCoefficient entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/currency_coefficient/testCurrencyCoefficientDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        CurrencyCoefficient entity = getCurrencyCoefficient();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<CurrencyCoefficient> entityList = generalEntityDao.findEntityList("CurrencyCoefficient.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private CurrencyCoefficient getCurrencyCoefficient() {
        Currency currencyFrom = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Currency currencyTo = Currency.builder()
                .id(2L)
                .label("EUR")
                .value("EUR")
                .build();
        return CurrencyCoefficient.builder()
                .id(1L)
                .coefficient(0.95d)
                .currencyFrom(currencyFrom)
                .currencyTo(currencyTo)
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
    }
}