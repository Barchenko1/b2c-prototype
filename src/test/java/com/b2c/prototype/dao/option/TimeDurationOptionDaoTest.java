package com.b2c.prototype.dao.option;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.option.TimeDurationOption;
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

class TimeDurationOptionDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/delivery/time_duration_option/emptyTimeDurationOptionDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE time_duration_option RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE price RESTART IDENTITY CASCADE"
    })
    @ExpectedDataSet(value = "datasets/dao/delivery/time_duration_option/saveTimeDurationOptionDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        TimeDurationOption entity = getTimeDurationOption();
        entity.setId(0);
        entity.getPrice().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/time_duration_option/testTimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/time_duration_option/updateTimeDurationOptionDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        TimeDurationOption entity = getTimeDurationOption();
        entity.setValue("Update 1-10");
        entity.setKey("Update 1-10");
        entity.getPrice().setAmount(20);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/time_duration_option/testTimeDurationOptionDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/delivery/time_duration_option/emptyTimeDurationOptionDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        TimeDurationOption entity = getTimeDurationOption();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/time_duration_option/testTimeDurationOptionDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        TimeDurationOption expected = getTimeDurationOption();

        Pair<String, Long> pair = Pair.of("id", 1L);
        TimeDurationOption entity = generalEntityDao.findEntity("TimeDurationOption.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/time_duration_option/testTimeDurationOptionDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        TimeDurationOption expected = getTimeDurationOption();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<TimeDurationOption> optionEntity = generalEntityDao.findOptionEntity("TimeDurationOption.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        TimeDurationOption entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/delivery/time_duration_option/testTimeDurationOptionDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        TimeDurationOption entity = getTimeDurationOption();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<TimeDurationOption> entityList = generalEntityDao.findEntityList("TimeDurationOption.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private TimeDurationOption getTimeDurationOption() {
        Currency currency = Currency.builder()
                .id(1L)
                .key("USD")
                .value("USD")
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(10)
                .currency(currency)
                .build();
        return TimeDurationOption.builder()
                .id(1L)
                .key("1-10")
                .value("1-10")
                .durationInMin(120)
                .price(price)
                .build();
    }

}