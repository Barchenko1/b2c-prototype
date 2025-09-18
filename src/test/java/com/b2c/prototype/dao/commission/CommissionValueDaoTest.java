package com.b2c.prototype.dao.commission;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.entity.payment.CommissionValue;
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

public class CommissionValueDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/commission/commission_value/emptyCommissionValueDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE commission_value RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/commission/commission_value/saveCommissionValueDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        CommissionValue entity = getCommissionValue();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/commission/commission_value/testCommissionValueDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/commission/commission_value/updateCommissionValueDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        CommissionValue entity = getCommissionValue();
        entity.setAmount(5);
        entity.setFeeType(FeeType.PERCENTAGE);
        entity.setCurrency(null);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/commission/commission_value/testCommissionValueDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/commission/commission_value/emptyCommissionValueDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        CommissionValue entity = getCommissionValue();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/commission/commission_value/testCommissionValueDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        CommissionValue expected = getCommissionValue();

        Pair<String, Long> pair = Pair.of("id", 1L);
        CommissionValue entity = generalEntityDao.findEntity("CommissionValue.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/commission/commission_value/testCommissionValueDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        CommissionValue expected = getCommissionValue();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<CommissionValue> optionEntity = generalEntityDao.findOptionEntity("CommissionValue.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        CommissionValue entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/commission/commission_value/testCommissionValueDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        CommissionValue entity = getCommissionValue();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<CommissionValue> entityList = generalEntityDao.findEntityList("CommissionValue.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private CommissionValue getCommissionValue() {
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .label("USD")
                .build();

        return CommissionValue.builder()
                .id(1L)
                .amount(20)
                .currency(currency)
                .feeType(FeeType.FIXED)
                .build();
    }
}
