package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemDataOptionQuantity;
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

class BasicItemDataOptionQuantityDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemDataOptionQuantity.class, "item_data_option_quantity"));
        entityIdentifierDao = new EntityIdentifierDao(entityMappingManager);
        dao = new BasicItemDataOptionQuantityDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_data_option_quantity");
            statement.execute("DELETE FROM item_data_option");
            statement.execute("DELETE FROM option_item");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/item_data_option_quantity/emptyItemDataOptionQuantityDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        ItemDataOptionQuantity itemDataOptionQuantity = ItemDataOptionQuantity.builder()
                .id(1L)
                .itemDataOption(prepareTestItemDataOption())
                .quantity(1)
                .build();
        return new EntityDataSet<>(itemDataOptionQuantity, "/datasets/item/item_data_option_quantity/testItemDataOptionQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ItemDataOptionQuantity itemDataOptionQuantity = ItemDataOptionQuantity.builder()
                .itemDataOption(prepareTestItemDataOption())
                .quantity(1)
                .build();
        return new EntityDataSet<>(itemDataOptionQuantity, "/datasets/item/item_data_option_quantity/saveItemDataOptionQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ItemDataOptionQuantity itemDataOptionQuantity = ItemDataOptionQuantity.builder()
                .id(1L)
                .itemDataOption(prepareTestItemDataOption())
                .quantity(2)
                .build();
        return new EntityDataSet<>(itemDataOptionQuantity, "/datasets/item/item_data_option_quantity/updateItemDataOptionQuantityDataSet.yml");
    }

    private ItemDataOption prepareTestItemDataOption() {
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
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
        Price price1 = Price.builder()
                .id(1L)
                .amount(100)
                .currency(currency)
                .build();
        Price price2 = Price.builder()
                .id(2L)
                .amount(20)
                .currency(currency)
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();

        return ItemDataOption.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .optionItem(optionItem)
                .articularId("1")
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }
}