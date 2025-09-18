package com.b2c.prototype.dao.option;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
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

class OptionItemCostDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/option/option_item_cost/emptyOptionItemCostDataSet.yml", cleanBefore = true,
            executeStatementsBefore = {
                    "TRUNCATE TABLE price RESTART IDENTITY CASCADE",
                    "TRUNCATE TABLE option_item_cost RESTART IDENTITY CASCADE",
            })
    @ExpectedDataSet(value = "datasets/option/option_item_cost/saveOptionItemCostDataSet.yml", orderBy = "id", ignoreCols = {"option_group_id"})
    public void persistEntity_success() {
        OptionItemCost entity = getOptionItemCost();
        entity.setId(0);
        entity.getOptionGroup().setId(0);
        entity.getPrice().setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/option/option_item_cost/testOptionItemCostDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/option/option_item_cost/updateOptionItemCostDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        OptionItemCost entity = getOptionItemCost();
        entity.setLabel("XL");
        entity.setValue("XL");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/option/option_item_cost/testOptionItemCostDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/option/option_item_cost/removeOptionItemCostDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        OptionItemCost entity = getOptionItemCost();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/option/option_item_cost/testOptionItemCostDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        OptionItemCost expected = getOptionItemCost();

        Pair<String, Long> pair = Pair.of("id", 1L);
        OptionItemCost entity = generalEntityDao.findEntity("OptionItemCost.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/option/option_item_cost/testOptionItemCostDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        OptionItemCost expected = getOptionItemCost();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<OptionItemCost> optionEntity = generalEntityDao.findOptionEntity("OptionItemCost.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        OptionItemCost entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/option/option_item_cost/testOptionItemCostDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        OptionItemCost entity = getOptionItemCost();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<OptionItemCost> entityList = generalEntityDao.findEntityList("OptionItemCost.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    public OptionItemCost getOptionItemCost() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Price price = Price.builder()
                .id(1L)
                .currency(currency)
                .amount(100)
                .build();
        OptionItemCost optionItemCost = OptionItemCost.builder()
                .id(1)
                .label("L")
                .value("L")
                .price(price)
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();

        optionGroup.addOptionItemCost(optionItemCost);
        return optionItemCost;
    }

}
