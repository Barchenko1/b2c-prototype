package com.b2c.prototype.dao.commission;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
import com.b2c.prototype.modal.entity.payment.MinMaxCommission;
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

public class MinMaxCommissionDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/commission/min_max_commission/emptyMinMaxCommissionDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE min_max_commission RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/dao/commission/min_max_commission/saveMinMaxCommissionDataSet.yml", orderBy = "amount", ignoreCols = {"id", "min_commission_id", "max_commission_id", "change_commission_price_id"})
    public void persistEntity_success() {
        MinMaxCommission entity = getMinMaxCommission();
        entity.setId(0);
        entity.getMinCommission().setId(0);
        entity.getMaxCommission().setId(0);
        entity.getChangeCommissionPrice().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/commission/min_max_commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/commission/min_max_commission/updateMinMaxCommissionDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        MinMaxCommission entity = getMinMaxCommission();

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/commission/min_max_commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/commission/min_max_commission/emptyMinMaxCommissionDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        MinMaxCommission entity = getMinMaxCommission();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/commission/min_max_commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        MinMaxCommission expected = getMinMaxCommission();

        Pair<String, Long> pair = Pair.of("id", 1L);
        MinMaxCommission entity = generalEntityDao.findEntity("MinMaxCommission.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/commission/min_max_commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        MinMaxCommission expected = getMinMaxCommission();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<MinMaxCommission> optionEntity = generalEntityDao.findOptionEntity("MinMaxCommission.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        MinMaxCommission entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/commission/min_max_commission/testMinMaxCommissionDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        MinMaxCommission entity = getMinMaxCommission();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<MinMaxCommission> entityList = generalEntityDao.findEntityList("MinMaxCommission.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private MinMaxCommission getMinMaxCommission() {
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .label("USD")
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(20)
                .currency(currency)
                .build();
        CommissionValue minCommission = CommissionValue.builder()
                .id(1L)
                .amount(20)
                .currency(currency)
                .feeType(FeeType.FIXED)
                .build();
        CommissionValue maxCommission = CommissionValue.builder()
                .id(2L)
                .amount(5)
                .currency(null)
                .feeType(FeeType.PERCENTAGE)
                .build();
        
        return MinMaxCommission.builder()
                .id(1L)
                .minCommission(minCommission)
                .maxCommission(maxCommission)
                .changeCommissionPrice(price)
                .lastUpdateTimestamp(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
    }
}
