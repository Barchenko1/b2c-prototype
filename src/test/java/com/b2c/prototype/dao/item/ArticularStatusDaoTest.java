package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticularStatusDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/item/articular_status/emptyArticularStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_status/saveArticularStatusDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ArticularStatus entity = getArticularStatus();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_status/testArticularStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_status/updateArticularStatusDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ArticularStatus entity = getArticularStatus();
        entity.setKey("Update Test");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_status/testArticularStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_status/emptyArticularStatusDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ArticularStatus entity = getArticularStatus();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_status/testArticularStatusDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ArticularStatus expected = getArticularStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ArticularStatus entity = generalEntityDao.findEntity("ArticularStatus.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_status/testArticularStatusDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ArticularStatus expected = getArticularStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ArticularStatus> optionEntity = generalEntityDao.findOptionEntity("ArticularStatus.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ArticularStatus entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_status/testArticularStatusDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ArticularStatus entity = getArticularStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ArticularStatus> entityList = generalEntityDao.findEntityList("ArticularStatus.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private ArticularStatus getArticularStatus() {
        return ArticularStatus.builder()
                .id(1L)
                .value("Test")
                .key("Test")
                .build();
    }

}