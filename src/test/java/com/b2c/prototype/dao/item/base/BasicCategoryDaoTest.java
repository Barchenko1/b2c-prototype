package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractTransitiveSelfEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Category;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.modal.TransitiveSelfEntity;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

class BasicCategoryDaoTest extends AbstractTransitiveSelfEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Category.class, "category"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicCategoryDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/category/emptyCategoryDataSet.yml";
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getTestDataSet() {
        Category parent = Category.builder()
                .id(1L)
                .name("parent")
                .build();
        Category root = Category.builder()
                .id(2L)
                .name("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .id(3L)
                .name("child")
                .build();
        parent.setChildNodeList(List.of(root));
        root.setParent(parent);
        root.setChildNodeList(List.of(child));
        child.setParent(root);
        return new EntityDataSet<>(root, "/datasets/item/category/testCategoryDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getSaveDataSet() {
        Category parent = Category.builder()
                .name("parent")
                .build();
        Category root = Category.builder()
                .name("root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .name("child")
                .build();

        parent.setChildNodeList(List.of(root));
        root.setParent(parent);
        root.setChildNodeList(List.of(child));
        child.setParent(root);
        return new EntityDataSet<>(root, "/datasets/item/category/saveCategoryDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getUpdateDataSet() {
        Category parent = Category.builder()
                .name("parent")
                .build();
        Category root = Category.builder()
                .name("Update root")
                .parent(parent)
                .build();
        Category child = Category.builder()
                .name("child")
                .build();

        parent.setChildNodeList(List.of(root));
        root.setParent(parent);
        root.setChildNodeList(List.of(child));
        child.setParent(root);
        return new EntityDataSet<>(root, "/datasets/item/category/updateCategoryDataSet.yml");
    }

    @Override
    protected EntityDataSet<? extends TransitiveSelfEntity> getDeleteDataSet() {
        Category parent = Category.builder()
                .id(1L)
                .name("parent")
                .build();

        return new EntityDataSet<>(parent,
                "/datasets/item/category/deleteCategoryDataSet.yml",
                            "/datasets/item/category/deleteChildCategoryDataSet.yml");
    }
}