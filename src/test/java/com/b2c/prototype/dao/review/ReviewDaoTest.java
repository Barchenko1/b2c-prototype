package com.b2c.prototype.dao.review;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.review.Review;
import com.b2c.prototype.modal.entity.review.ReviewStatus;
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

class ReviewDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/review/review/emptyReviewDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/review/review/saveReviewDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Review entity = getReview();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review/testReviewDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/review/review/updateReviewDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Review entity = getReview();
        entity.setTitle("Update Great Product!");
        entity.setMessage("Update Really enjoyed using this product. Highly recommend it!");
        Rating rating = entity.getRating();
        rating.setId(1L);
        rating.setValue(1);

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review/testReviewDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/review/review/emptyReviewDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Review entity = getReview();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review/testReviewDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Review expected = getReview();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Review entity = generalEntityDao.findEntity("Review.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review/testReviewDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Review expected = getReview();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Review> optionEntity = generalEntityDao.findOptionEntity("Review.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Review entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/review/review/testReviewDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Review entity = getReview();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Review> entityList = generalEntityDao.findEntityList("Review.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Review getReview() {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();
        ReviewStatus reviewStatus = ReviewStatus.builder()
                .id(1L)
                .key("Pending")
                .value("PENDING")
                .build();
        return Review.builder()
                .id(1L)
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .title("Great Product!")
                .message("Really enjoyed using this product. Highly recommend it!")
                .status(reviewStatus)
                .rating(rating)
                .reviewUniqId("review123")
                .comments(List.of())
                .build();
    }
}
