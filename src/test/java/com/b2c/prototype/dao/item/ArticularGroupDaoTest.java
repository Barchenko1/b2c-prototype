package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ArticularGroup;
import com.b2c.prototype.modal.entity.item.Category;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArticularGroupDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/dao/item/articular_group/emptyArticularGroupDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE item_type RESTART IDENTITY CASCADE",
            "TRUNCATE TABLE category RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/dao/item/articular_group/saveArticularGroupDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        ArticularGroup entity = getArticularGroup();
        entity.setId(0);
        Category category = Category.builder()
                .id(0L)
                .value("parent")
                .key("parent")
                .build();
//       entity.setCategory(category);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_group/testArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_group/updateArticularGroupDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        ArticularGroup entity = getArticularGroup();
        entity.setDescription(new HashMap<>() {{
            put("desc1", "desc1");
            put("desc2", "desc2");
            put("desc3", "desc3");
        }});

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_group/testArticularGroupDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/dao/item/articular_group/removeArticularGroupDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        ArticularGroup entity = getArticularGroup();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_group/testArticularGroupDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        ArticularGroup expected = getArticularGroup();

        Pair<String, Long> pair = Pair.of("id", 1L);
        ArticularGroup entity = generalEntityDao.findEntity("ArticularGroup.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_group/testArticularGroupDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        ArticularGroup expected = getArticularGroup();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<ArticularGroup> optionEntity = generalEntityDao.findOptionEntity("ArticularGroup.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        ArticularGroup entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/dao/item/articular_group/testArticularGroupDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        ArticularGroup entity = getArticularGroup();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<ArticularGroup> entityList = generalEntityDao.findEntityList("ArticularGroup.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Category getCategory() {
        Category parent = Category.builder()
                .id(1L)
                .key("parent")
                .value("parent")
                .build();
        Category root = Category.builder()
                .id(2L)
                .key("root")
                .value("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .id(3L)
                .key("child")
                .value("child")
                .build();

        parent.setChildList(List.of(root));
        root.setParent(parent);
        root.setChildList(List.of(child));
        child.setParent(root);

        return child;
    }

    private ArticularGroup getArticularGroup() {

        Category category = getCategory();

        Map<String, String> description = new HashMap<>(){{
            put("desc1", "desc1");
            put("desc2", "desc2");
        }};

        return ArticularGroup.builder()
                .id(1L)
                .articularGroupId("123")
                .description(description)
//                .category(category)
                .build();
    }

}
