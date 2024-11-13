package com.b2c.prototype.dao.item.base;

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
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

class BasicItemDataDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemData.class, "item_data"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicItemDataDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_data_option");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
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

    private ItemData prepareItemData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .value("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .optionName("M")
                .optionGroup(optionGroup)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        ItemData itemData = ItemData.builder()
                .id(1L)
                .name("name")
                .articularId("100")
                .dateOfCreate(100L)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .price(price)
                .build();

        itemData.addOptionItem(optionItem1);
        itemData.addOptionItem(optionItem2);

        return itemData;
    }

    private ItemData prepareSaveItemData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .value("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .optionName("M")
                .optionGroup(optionGroup)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        ItemData itemData = ItemData.builder()
                .name("name")
                .articularId("100")
                .dateOfCreate(100L)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .price(price)
                .build();

        itemData.addOptionItem(optionItem1);
        itemData.addOptionItem(optionItem2);

        return itemData;
    }

    private ItemData prepareUpdateItemData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .value("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .optionName("M")
                .optionGroup(optionGroup)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();

        ItemData itemData = ItemData.builder()
                .id(1L)
                .name("Update name")
                .articularId("200")
                .dateOfCreate(200L)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .price(price)
                .build();

        itemData.addOptionItem(optionItem1);
        itemData.addOptionItem(optionItem2);

        return itemData;
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/item_data/emptyItemDataDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        ItemData itemData = prepareItemData();
        return new EntityDataSet<>(itemData, "/datasets/item/item_data/testItemDataDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ItemData itemData = prepareSaveItemData();
        return new EntityDataSet<>(itemData, "/datasets/item/item_data/saveItemDataDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ItemData itemData = prepareUpdateItemData();
        return new EntityDataSet<>(itemData, "/datasets/item/item_data/updateItemDataDataSet.yml");
    }
}