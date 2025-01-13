package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.AbstractSimpleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
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

import java.util.List;

class BasicStoreDaoTest extends AbstractSimpleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Store.class, "store"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicStoreDao(sessionFactory, entityIdentifierDao);
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
                .label("limited")
                .build();
        Store store = Store.builder()
                .id(1L)
                .itemDataOption(prepareTestItemDataOption())
                .countType(countType)
                .count(10)
                .build();
        return new EntityDataSet<>(store, "/datasets/store/store/testStoreDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        CountType countType = CountType.builder()
                .id(1L)
                .value("LIMITED")
                .label("limited")
                .build();
        Store store = Store.builder()
                .itemDataOption(prepareTestItemDataOption())
                .countType(countType)
                .count(10)
                .build();
        return new EntityDataSet<>(store, "/datasets/store/store/saveStoreDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        CountType countType = CountType.builder()
                .id(1L)
                .value("LIMITED")
                .label("limited")
                .build();
        Store store = Store.builder()
                .id(1L)
                .countType(countType)
                .itemDataOption(prepareTestItemDataOption())
                .count(9)
                .build();
        return new EntityDataSet<>(store, "/datasets/store/store/updateStoreDataSet.yml");
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

    private ItemDataOption prepareTestItemDataOption() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .value("NEW")
                .label("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .label("Clothes")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        ItemData itemData = ItemData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .status(itemStatus)
                .itemType(itemType)
                .build();

        return ItemDataOption.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .optionItem(optionItem)
                .articularId("1")
                .build();
    }

}