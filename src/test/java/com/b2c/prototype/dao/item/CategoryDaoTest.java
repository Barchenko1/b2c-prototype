package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.Category;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CategoryDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/item/category/emptyCategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/category/saveCategoryDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Category entity = getCategoryChain();
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
    @DataSet(value = "datasets/item/category/testCategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/category/updateCategoryDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Category entity = getCategoryChain();
        entity.getChildList().forEach(childEntity -> {
            childEntity.setLabel("Update root");
            childEntity.setValue("Update root");
        });

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/category/testCategoryDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/category/emptyCategoryDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Category entity = getCategoryChain();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/category/testCategoryDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Category expected = getCategoryChain();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Category entity = generalEntityDao.findEntity("Category.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/category/testCategoryDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Category expected = getCategoryChain();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Category> optionEntity = generalEntityDao.findOptionEntity("Category.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Category entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/category/testCategoryDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Category entity = getCategoryChain();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Category> entityList = generalEntityDao.findEntityList("Category.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }
    
    private Category getCategoryChain() {
        Category parent = Category.builder()
                .id(1L)
                .label("parent")
                .value("parent")
                .build();
        Category root = Category.builder()
                .id(2L)
                .label("root")
                .value("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .id(3L)
                .label("child")
                .value("child")
                .build();

        parent.addChildEntity(root);
        root.addChildEntity(child);
        
        return parent;
    }

}