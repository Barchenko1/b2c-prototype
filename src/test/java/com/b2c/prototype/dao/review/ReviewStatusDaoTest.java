package com.b2c.prototype.dao.review;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReviewStatusDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/review/review_status/emptyReviewStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/review/review_status/saveReviewStatusDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ReviewStatus entity = getReviewStatus();
        entity.setId(0L);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review_status/testReviewStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/review/review_status/updateReviewStatusDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ReviewStatus entity = getReviewStatus();
        entity.setValue("new");
        entity.setKey("new");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review_status/testReviewStatusDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/review/review_status/emptyReviewStatusDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ReviewStatus entity = getReviewStatus();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review_status/testReviewStatusDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ReviewStatus expected = getReviewStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ReviewStatus entity = generalEntityDao.findEntity("ReviewStatus.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review_status/testReviewStatusDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ReviewStatus expected = getReviewStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ReviewStatus> optionEntity = generalEntityDao.findOptionEntity("ReviewStatus.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ReviewStatus entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review_status/testReviewStatusDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ReviewStatus entity = getReviewStatus();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ReviewStatus> entityList = generalEntityDao.findEntityList("ReviewStatus.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private ReviewStatus getReviewStatus() {

        return ReviewStatus.builder()
                .id(1L)
                .key("Pending")
                .value("Pending")
                .build();
    }

}
