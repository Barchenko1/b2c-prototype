package com.b2c.prototype.dao.post.base;

import com.b2c.prototype.dao.AbstractTransitiveSelfEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.post.Post;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicPostDaoTest extends AbstractTransitiveSelfEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Post.class, "post"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicPostDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/post/emptyPostDataSet.yml";
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getTestDataSet() {
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .uniquePostId("1")
                .authorEmail("parent@email.com")
                .authorUserName("parent")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .id(2L)
                .title("root")
                .uniquePostId("2")
                .authorEmail("root@email.com")
                .authorUserName("root")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .id(3L)
                .title("child")
                .uniquePostId("3")
                .authorEmail("child@email.com")
                .authorUserName("child")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildPost(root);
        root.addChildPost(child);
        return new EntityDataSet<>(root, "/datasets/post/testPostDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getSaveDataSet() {
        Post parent = Post.builder()
                .title("parent")
                .uniquePostId("1")
                .authorEmail("parent@email.com")
                .authorUserName("parent")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .title("root")
                .uniquePostId("2")
                .authorEmail("root@email.com")
                .authorUserName("root")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .title("child")
                .uniquePostId("3")
                .authorEmail("child@email.com")
                .authorUserName("child")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildPost(root);
        root.addChildPost(child);
        return new EntityDataSet<>(root, "/datasets/post/savePostDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getUpdateDataSet() {
        Post parent = Post.builder()
                .title("parent")
                .uniquePostId("1")
                .authorEmail("parent@email.com")
                .authorUserName("parent")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .title("Update root")
                .uniquePostId("2")
                .authorEmail("root@email.com")
                .authorUserName("root")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .title("child")
                .uniquePostId("3")
                .authorEmail("child@email.com")
                .authorUserName("child")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildPost(root);
        root.addChildPost(child);
        return new EntityDataSet<>(root, "/datasets/post/updatePostDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getDeleteDataSet() {
        Post parent = Post.builder()
                .title("parent")
                .uniquePostId("1")
                .authorEmail("parent@email.com")
                .authorUserName("parent")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .title("root")
                .uniquePostId("2")
                .authorEmail("root@email.com")
                .authorUserName("root")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .title("child")
                .uniquePostId("3")
                .authorEmail("child@email.com")
                .authorUserName("child")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildPost(root);
        root.addChildPost(child);
        return new EntityDataSet<>(root,
                "/datasets/post/deletePostDataSet.yml",
                            "/datasets/post/deleteChildPostDataSet.yml");
    }
}