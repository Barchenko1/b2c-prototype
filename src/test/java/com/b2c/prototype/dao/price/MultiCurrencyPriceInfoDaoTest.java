package com.b2c.prototype.dao.price;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.b2c.prototype.modal.entity.payment.MultiCurrencyPriceInfo;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
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

class MultiCurrencyPriceInfoDaoTest extends AbstractDaoTest {
    
    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/price/multi_currency_price_info/emptyMultiCurrencyPriceInfoDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/multi_currency_price_info/saveMultiCurrencyPriceInfoDataSet.yml", orderBy = "amount", ignoreCols = {"id", "original_price_id", "current_price_id"})
    public void persistEntity_success() {
        MultiCurrencyPriceInfo entity = getMultiCurrencyPriceInfo();
        entity.setId(0);
        entity.getOriginalPrice().setId(0);
        entity.getCurrentPrice().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/multi_currency_price_info/testMultiCurrencyPriceInfoDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/multi_currency_price_info/updateMultiCurrencyPriceInfoDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        MultiCurrencyPriceInfo entity = getMultiCurrencyPriceInfo();
//        entity.setAmount(20);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/multi_currency_price_info/testMultiCurrencyPriceInfoDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/price/multi_currency_price_info/emptyMultiCurrencyPriceInfoDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        MultiCurrencyPriceInfo entity = getMultiCurrencyPriceInfo();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/multi_currency_price_info/testMultiCurrencyPriceInfoDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        MultiCurrencyPriceInfo expected = getMultiCurrencyPriceInfo();

        Pair<String, Long> pair = Pair.of("id", 1L);
        MultiCurrencyPriceInfo entity = generalEntityDao.findEntity("MultiCurrencyPriceInfo.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/multi_currency_price_info/testMultiCurrencyPriceInfoDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        MultiCurrencyPriceInfo expected = getMultiCurrencyPriceInfo();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<MultiCurrencyPriceInfo> optionEntity = generalEntityDao.findOptionEntity("MultiCurrencyPriceInfo.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        MultiCurrencyPriceInfo entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/price/multi_currency_price_info/testMultiCurrencyPriceInfoDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        MultiCurrencyPriceInfo entity = getMultiCurrencyPriceInfo();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<MultiCurrencyPriceInfo> entityList = generalEntityDao.findEntityList("MultiCurrencyPriceInfo.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private MultiCurrencyPriceInfo getMultiCurrencyPriceInfo() {
        Currency currencyUsd = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Currency currencyEur = Currency.builder()
                .id(2L)
                .label("EUR")
                .value("EUR")
                .build();

        Price priceUsd = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currencyUsd)
                .build();
        Price priceEur = Price.builder()
                .id(2L)
                .amount(95)
                .currency(currencyEur)
                .build();

        CurrencyCoefficient currencyCoefficient = CurrencyCoefficient.builder()
                .id(1L)
                .currencyFrom(priceUsd.getCurrency())
                .currencyTo(priceEur.getCurrency())
                .coefficient(0.95)
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();

        return MultiCurrencyPriceInfo.builder()
                .id(1L)
                .originalPrice(priceUsd)
                .currencyCoefficient(currencyCoefficient)
                .currentPrice(priceEur)
                .build();
    }
}