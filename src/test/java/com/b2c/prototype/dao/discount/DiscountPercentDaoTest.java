package com.b2c.prototype.dao.discount;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.Discount;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DiscountPercentDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/item/discount/percent/emptyDiscountDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE discount RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/dao/item/discount/percent/saveDiscountDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Discount entity = getDiscount();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/discount/percent/testDiscountDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/discount/percent/updateDiscountDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Discount entity = getDiscount();
        entity.setAmount(20);
        entity.setActive(true);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/discount/percent/testDiscountDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/discount/percent/emptyDiscountDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Discount entity = getDiscount();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/discount/percent/testDiscountDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Discount expected = getDiscount();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Discount entity = generalEntityDao.findEntity("Discount.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/discount/percent/testDiscountDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Discount expected = getDiscount();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Discount> optionEntity = generalEntityDao.findOptionEntity("Discount.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Discount entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/discount/percent/testDiscountDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Discount entity = getDiscount();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Discount> entityList = generalEntityDao.findEntityList("Discount.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private Discount getDiscount() {
        return Discount.builder()
                .id(1L)
                .amount(10)
                .charSequenceCode("abc")
                .isPercent(true)
                .isActive(false)
                .build();
    }

}