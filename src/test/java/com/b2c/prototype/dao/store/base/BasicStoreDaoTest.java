package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

class BasicStoreDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Store.class, "store"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicStoreDao(sessionFactory, entityIdentifierDao);
    }

    private Category prepareCategories() {
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

        return child;
    }

    private OptionItem prepareOptionItem() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();

        return OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/store/store/emptyStoreDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        CountType countType = CountType.builder()
                .id(1L)
                .value("LIMITED")
                .build();
        Store store = Store.builder()
                .id(1L)
                .countType(countType)
                .count(10)
                .optionItem(prepareOptionItem())
                .build();
        return new EntityDataSet<>(store, "/datasets/store/store/testStoreDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CountType countType = CountType.builder()
                .id(1L)
                .value("LIMITED")
                .build();
        Store store = Store.builder()
                .countType(countType)
                .count(10)
                .optionItem(prepareOptionItem())
                .build();
        return new EntityDataSet<>(store, "/datasets/store/store/saveStoreDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        CountType countType = CountType.builder()
                .id(1L)
                .value("LIMITED")
                .build();
        Store store = Store.builder()
                .id(1L)
                .countType(countType)
                .count(9)
                .optionItem(prepareOptionItem())
                .build();
        return new EntityDataSet<>(store, "/datasets/store/store/updateStoreDataSet.yml");
    }
}