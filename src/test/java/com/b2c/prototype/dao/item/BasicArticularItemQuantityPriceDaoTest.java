package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularItemQuantityPrice;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.process.dao.query.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Statement;

class BasicArticularItemQuantityPriceDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ArticularItemQuantityPrice.class, "articular_item_quantity"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicItemDataOptionQuantityDao(sessionFactory, queryService);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM articular_item_quantity");
            statement.execute("DELETE FROM articular_item_option_item");
            statement.execute("DELETE FROM articular_item");
            statement.execute("DELETE FROM option_item");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/item/articular_item_quantity/emptyArticularItemQuantityDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        ArticularItemQuantityPrice articularItemQuantityPrice = ArticularItemQuantityPrice.builder()
                .id(1L)
//                .articularItem(prepareTestItemDataOption())
//                .quantity(1)
                .build();
        return new EntityDataSet<>(articularItemQuantityPrice, "/datasets/item/articular_item_quantity/testArticularItemQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ArticularItemQuantityPrice articularItemQuantityPrice = ArticularItemQuantityPrice.builder()
//                .articularItem(prepareTestItemDataOption())
//                .quantity(1)
                .build();
        return new EntityDataSet<>(articularItemQuantityPrice, "/datasets/item/articular_item_quantity/saveArticularItemQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ArticularItemQuantityPrice articularItemQuantityPrice = ArticularItemQuantityPrice.builder()
                .id(1L)
//                .articularItem(prepareTestItemDataOption())
//                .quantity(2)
                .build();
        return new EntityDataSet<>(articularItemQuantityPrice, "/datasets/item/articular_item_quantity/updateArticularItemQuantityDataSet.yml");
    }

    private ArticularItem prepareTestItemDataOption() {
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
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .value("M")
                .label("M")
                .build();
        optionGroup.addOptionItem(optionItem1);
        optionGroup.addOptionItem(optionItem2);
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

        ArticularItem articularItem = ArticularItem.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .articularId("1")
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();

        articularItem.addOptionItem(optionItem1);
        articularItem.addOptionItem(optionItem2);
        return articularItem;
    }
}