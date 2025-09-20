package com.b2c.prototype.dao.post;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.post.Post;
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

class PostDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/post/emptyPostDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/post/savePostDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Post entity = getPostChain();
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
    @DataSet(value = "datasets/dao/post/testPostDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/post/updatePostDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Post entity = getPostChain();
        entity.getChildList().forEach(childEntity -> {
            childEntity.setTitle("Update root");
        });

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/post/testPostDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/post/emptyPostDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Post entity = getPostChain();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/post/testPostDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Post expected = getPostChain();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Post entity = generalEntityDao.findEntity("Post.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/post/testPostDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Post expected = getPostChain();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Post> optionEntity = generalEntityDao.findOptionEntity("Post.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Post entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/post/testPostDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Post entity = getPostChain();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Post> entityList = generalEntityDao.findEntityList("Post.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Post getPostChain() {
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .postUniqId("1")
                .message("parent")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
        Post root = Post.builder()
                .id(2L)
                .title("root")
                .postUniqId("2")
                .message("root")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
        Post child = Post.builder()
                .id(3L)
                .title("child")
                .postUniqId("3")
                .message("child")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
        parent.addChildPost(root);
        root.addChildPost(child);

        return parent;
    }

}
