package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Category;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

class BasicCategoryDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Category.class, "category"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicCategoryDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/category/emptyCategoryDataSet.yml";
    }

    @Override
    protected EntityDataSet<Category> getTestDataSet() {
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
        return new EntityDataSet<>(root, "/datasets/item/category/testCategoryDataSet.yml");
    }

    @Override
    protected EntityDataSet<Category> getSaveDataSet() {
        Category parent = Category.builder()
                .label("parent")
                .value("parent")
                .build();
        Category root = Category.builder()
                .label("root")
                .value("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .label("child")
                .value("child")
                .build();

        parent.addChildEntity(root);
        root.addChildEntity(child);
        return new EntityDataSet<>(root, "/datasets/item/category/saveCategoryDataSet.yml");
    }

    @Override
    protected EntityDataSet<Category> getUpdateDataSet() {
        Category parent = Category.builder()
                .label("parent")
                .value("parent")
                .build();
        Category root = Category.builder()
                .label("Update root")
                .value("Update root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .label("child")
                .value("child")
                .build();

        parent.addChildEntity(root);
        root.addChildEntity(child);
        return new EntityDataSet<>(root, "/datasets/item/category/updateCategoryDataSet.yml");
    }

}