package com.b2c.prototype.dao.store.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.store.CountType;
import com.b2c.prototype.modal.entity.store.Store;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

class BasicStoreDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Store.class, "store"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicStoreDao(sessionFactory, queryService);
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
//                .articularItemQuantities(prepareTestItemDataOption())
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
//                .articularItem(prepareTestItemDataOption())
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
//                .articularItem(prepareTestItemDataOption())
                .build();
        return new EntityDataSet<>(store, "/datasets/store/store/updateStoreDataSet.yml");
    }

    private Category prepareCategories() {
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

        parent.setChildList(List.of(root));
        root.setParent(parent);
        root.setChildList(List.of(child));
        child.setParent(root);

        return child;
    }

    private ArticularItem prepareTestItemDataOption() {
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
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .value("NEW")
                .label("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .label("Clothes")
                .build();
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();
        optionGroup.addOptionItem(optionItem);
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        ItemData itemData = ItemData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();
        ArticularItem articularItem = ArticularItem.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .articularId("1")
                .build();
        articularItem.addOptionItem(optionItem);

        return articularItem;
    }
}