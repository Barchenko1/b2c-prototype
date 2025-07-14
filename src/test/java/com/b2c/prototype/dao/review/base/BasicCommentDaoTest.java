package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.review.ReviewComment;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCommentDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ReviewComment.class, "reviewComment"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicCommentDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/review/comment/emptyCommentDataSet.yml";
    }

    @Override
    protected EntityDataSet<ReviewComment> getTestDataSet() {
        ReviewComment parent = ReviewComment.builder()
                .id(1L)
                .title("parent")
                .commentId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        ReviewComment root = ReviewComment.builder()
                .id(2L)
                .title("root")
                .commentId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        ReviewComment child = ReviewComment.builder()
                .id(3L)
                .title("child")
                .commentId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);
        return new EntityDataSet<>(root, "/datasets/review/comment/testCommentDataSet.yml");
    }

    @Override
    protected EntityDataSet<ReviewComment> getSaveDataSet() {
        ReviewComment parent = ReviewComment.builder()
                .title("parent")
                .commentId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        ReviewComment root = ReviewComment.builder()
                .title("root")
                .commentId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        ReviewComment child = ReviewComment.builder()
                .title("child")
                .commentId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);
        return new EntityDataSet<>(root, "/datasets/review/comment/saveCommentDataSet.yml");
    }

    @Override
    protected EntityDataSet<ReviewComment> getUpdateDataSet() {
        ReviewComment parent = ReviewComment.builder()
                .id(1L)
                .title("parent")
                .commentId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        ReviewComment root = ReviewComment.builder()
                .id(2L)
                .title("Update root")
                .commentId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        ReviewComment child = ReviewComment.builder()
                .id(3L)
                .title("child")
                .commentId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);
        return new EntityDataSet<>(root, "/datasets/review/comment/updateCommentDataSet.yml");
    }

//    @Override
//    protected EntityDataSet<? extends TransitiveSelfEntity> getDeleteDataSet() {
//        Comment parent = Comment.builder()
//                .title("parent")
//                .uniqueCommentId("1")
//                .message("parent")
//                .dateOfCreate(100000)
//                .build();
//        Comment root = Comment.builder()
//                .title("root")
//                .uniqueCommentId("2")
//                .message("root")
//                .dateOfCreate(100000)
//                .build();
//        Comment child = Comment.builder()
//                .title("child")
//                .uniqueCommentId("3")
//                .message("child")
//                .dateOfCreate(100000)
//                .build();
//
//        parent.addChildComment(root);
//        root.addChildComment(child);
//
//        return new EntityDataSet<>(root,
//                "/datasets/review/comment/deleteCommentDataSet.yml",
//                "/datasets/review/comment/deleteChildCommentDataSet.yml");
//    }
}