package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
import com.b2c.prototype.modal.entity.item.ItemDataOptionQuantity;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Review;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

class BasicItemDataOptionQuantityDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemDataOptionQuantity.class, "item_data_option_quantity"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
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
        return "/datasets/item/item_data_quantity/emptyItemQuantityDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        ItemDataOptionQuantity itemDataOptionQuantity = ItemDataOptionQuantity.builder()
                .id(1L)
                .itemDataOption(prepareTestItemDataOption())
                .quantity(1)
                .build();
        return new EntityDataSet<>(itemDataOptionQuantity, "/datasets/item/item_data_quantity/testItemQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ItemDataOptionQuantity itemDataOptionQuantity = ItemDataOptionQuantity.builder()
                .itemDataOption(prepareTestItemDataOption())
                .quantity(1)
                .build();
        return new EntityDataSet<>(itemDataOptionQuantity, "/datasets/item/item_data_quantity/saveItemQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ItemDataOptionQuantity itemDataOptionQuantity = ItemDataOptionQuantity.builder()
                .id(1L)
                .itemDataOption(prepareTestItemDataOption())
                .quantity(2)
                .build();
        return new EntityDataSet<>(itemDataOptionQuantity, "/datasets/item/item_data_quantity/updateItemQuantityDataSet.yml");
    }

    private ItemDataOption prepareTestItemDataOption() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
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

    private void addReview(Item item) {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();

        Review review = Review.builder()
                .id(1L)
                .title("title")
                .message("message")
                .dateOfCreate(100)
                .rating(rating)
                .build();

//        item.addReview(review);
    }

    private void addPosts(Item item) {
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .uniquePostId("1")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .id(2L)
                .title("root")
                .uniquePostId("2")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .id(3L)
                .title("child")
                .uniquePostId("3")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildPost(root);
        root.addChildPost(child);

//        item.addPost(parent);
//        item.addPost(root);
//        item.addPost(child);
    }
}