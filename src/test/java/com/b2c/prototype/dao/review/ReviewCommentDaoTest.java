package com.b2c.prototype.dao.review;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.review.ReviewComment;
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

class ReviewCommentDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/review/comment/emptyCommentDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/review/comment/saveCommentDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ReviewComment entity = getReviewComment();
        entity.setId(0);
        entity.getChildList().forEach(childEntity -> {
            childEntity.setId(0);
            childEntity.getChildList().forEach(childChildEntity -> {
                childChildEntity.setId(0);
            });
        });

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/review/comment/testCommentDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/review/comment/updateCommentDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ReviewComment entity = getReviewComment();
        entity.getChildList().forEach(child -> {
            child.setTitle("Update root");
        });

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/review/comment/testCommentDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/review/comment/emptyCommentDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ReviewComment entity = getReviewComment();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/review/comment/testCommentDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ReviewComment expected = getReviewComment();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ReviewComment entity = generalEntityDao.findEntity("ReviewComment.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/review/comment/testCommentDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ReviewComment expected = getReviewComment();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ReviewComment> optionEntity = generalEntityDao.findOptionEntity("ReviewComment.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ReviewComment entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/review/comment/testCommentDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ReviewComment entity = getReviewComment();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ReviewComment> entityList = generalEntityDao.findEntityList("ReviewComment.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private ReviewComment getReviewComment() {
        ReviewComment parent = ReviewComment.builder()
                .id(1L)
                .title("parent")
                .reviewCommentUniqId("1")
                .message("parent")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
        ReviewComment root = ReviewComment.builder()
                .id(2L)
                .title("root")
                .reviewCommentUniqId("2")
                .message("root")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
        ReviewComment child = ReviewComment.builder()
                .id(3L)
                .title("child")
                .reviewCommentUniqId("3")
                .message("child")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);
        
        return parent;
    }

}
