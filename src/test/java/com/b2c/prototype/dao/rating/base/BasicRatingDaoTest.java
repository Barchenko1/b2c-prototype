package com.b2c.prototype.dao.rating.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Rating;
import org.junit.jupiter.api.BeforeAll;

class BasicRatingDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        dao = new BasicRatingDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/rating/emptyRatingDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Rating rating = Rating.builder()
                .id(1L)
                .value(5)
                .build();
        return new EntityDataSet<>(rating, "/datasets/rating/testRatingDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Rating rating = Rating.builder()
                .value(5)
                .build();
        return new EntityDataSet<>(rating, "/datasets/rating/saveRatingDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Rating rating = Rating.builder()
                .id(1L)
                .value(3)
                .build();
        return new EntityDataSet<>(rating, "/datasets/rating/updateRatingDataSet.yml");
    }

}