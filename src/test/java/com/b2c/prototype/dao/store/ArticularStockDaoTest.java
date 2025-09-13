package com.b2c.prototype.dao.store;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.constant.CountType;
import com.b2c.prototype.modal.entity.store.ArticularStock;
import com.b2c.prototype.modal.entity.store.AvailabilityStatus;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ArticularStockDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/store/articular_stock/emptyArticularStockDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/store/articular_stock/saveArticularStockDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ArticularStock entity = getArticularStock();
        entity.setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/store/articular_stock/testArticularStockDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/store/articular_stock/updateArticularStockDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ArticularStock entity = getArticularStock();
        
        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/store/articular_stock/testArticularStockDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/store/articular_stock/emptyArticularStockDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ArticularStock entity = getArticularStock();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/store/articular_stock/testArticularStockDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ArticularStock expected = getArticularStock();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ArticularStock entity = generalEntityDao.findEntity("ArticularStock.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/store/articular_stock/testArticularStockDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ArticularStock expected = getArticularStock();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ArticularStock> optionEntity = generalEntityDao.findOptionEntity("ArticularStock.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ArticularStock entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/store/articular_stock/testArticularStockDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ArticularStock entity = getArticularStock();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ArticularStock> entityList = generalEntityDao.findEntityList("ArticularStock.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private ArticularStock getArticularStock() {
        AvailabilityStatus availabilityStatus = AvailabilityStatus.builder().build();
        return ArticularStock.builder()
                .id(1L)
                .articularItemQuantities(List.of())
                .availabilityState()
                .countType(CountType.LIMITED)
                .build();
    }
}
