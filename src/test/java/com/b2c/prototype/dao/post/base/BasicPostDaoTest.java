package com.b2c.prototype.dao.post.base;

import com.b2c.prototype.dao.AbstractTransitiveSelfEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.post.Post;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicPostDaoTest extends AbstractTransitiveSelfEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Post.class, "post"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicPostDao(sessionFactory, queryService);
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
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .id(2L)
                .title("root")
                .uniquePostId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .id(3L)
                .title("child")
                .uniquePostId("3")
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
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .title("root")
                .uniquePostId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .title("child")
                .uniquePostId("3")
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
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .title("Update root")
                .uniquePostId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .title("child")
                .uniquePostId("3")
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
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .title("root")
                .uniquePostId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .title("child")
                .uniquePostId("3")
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