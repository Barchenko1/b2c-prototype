package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.dao.AbstractTransitiveSelfEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.review.Comment;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCommentDaoTest extends AbstractTransitiveSelfEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Comment.class, "comment"));
        entityIdentifierDao = new EntityIdentifierDao(entityMappingManager);
        dao = new BasicCommentDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/review/comment/emptyCommentDataSet.yml";
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getTestDataSet() {
        Comment parent = Comment.builder()
                .id(1L)
                .title("parent")
                .uniqueCommentId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Comment root = Comment.builder()
                .id(2L)
                .title("root")
                .uniqueCommentId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Comment child = Comment.builder()
                .id(3L)
                .title("child")
                .uniqueCommentId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);
        return new EntityDataSet<>(root, "/datasets/review/comment/testCommentDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getSaveDataSet() {
        Comment parent = Comment.builder()
                .title("parent")
                .uniqueCommentId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Comment root = Comment.builder()
                .title("root")
                .uniqueCommentId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Comment child = Comment.builder()
                .title("child")
                .uniqueCommentId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);
        return new EntityDataSet<>(root, "/datasets/review/comment/saveCommentDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getUpdateDataSet() {
        Comment parent = Comment.builder()
                .id(1L)
                .title("parent")
                .uniqueCommentId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Comment root = Comment.builder()
                .id(2L)
                .title("Update root")
                .uniqueCommentId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Comment child = Comment.builder()
                .id(3L)
                .title("child")
                .uniqueCommentId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);
        return new EntityDataSet<>(root, "/datasets/review/comment/updateCommentDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getDeleteDataSet() {
        Comment parent = Comment.builder()
                .title("parent")
                .uniqueCommentId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Comment root = Comment.builder()
                .title("root")
                .uniqueCommentId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Comment child = Comment.builder()
                .title("child")
                .uniqueCommentId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildComment(root);
        root.addChildComment(child);

        return new EntityDataSet<>(root,
                "/datasets/review/comment/deleteCommentDataSet.yml",
                "/datasets/review/comment/deleteChildCommentDataSet.yml");
    }
}