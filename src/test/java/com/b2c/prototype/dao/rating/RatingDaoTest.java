package com.b2c.prototype.dao.rating;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.review.Rating;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RatingDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/rating/emptyRatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/rating/saveRatingDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Rating entity = getRating();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/rating/testRatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/rating/updateRatingDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Rating entity = getRating();
        entity.setValue(3);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/rating/testRatingDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/rating/emptyRatingDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Rating entity = getRating();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/rating/testRatingDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Rating expected = getRating();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Rating entity = generalEntityDao.findEntity("Rating.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/rating/testRatingDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Rating expected = getRating();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Rating> optionEntity = generalEntityDao.findOptionEntity("Rating.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Rating entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/rating/testRatingDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Rating entity = getRating();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Rating> entityList = generalEntityDao.findEntityList("Rating.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private Rating getRating() {
        return Rating.builder()
                .id(1L)
                .value(5)
                .build();
    }

}
