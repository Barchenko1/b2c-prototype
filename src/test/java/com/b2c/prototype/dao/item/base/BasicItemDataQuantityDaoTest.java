package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemDataQuantity;
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
import java.util.Set;

class BasicItemDataQuantityDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemDataQuantity.class, "item_data_quantity"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicItemQuantityDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_data_quantity_item_data");
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
        ItemData itemData = prepareTestItemData();
        ItemDataQuantity itemDataQuantity = ItemDataQuantity.builder()
                .id(1L)
                .itemDataSet(Set.of(itemData))
                .quantity(1)
                .build();
        return new EntityDataSet<>(itemDataQuantity, "/datasets/item/item_data_quantity/testItemQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        ItemData itemData = prepareTestItemData();
        ItemDataQuantity itemDataQuantity = ItemDataQuantity.builder()
                .itemDataSet(Set.of(itemData))
                .quantity(1)
                .build();
        return new EntityDataSet<>(itemDataQuantity, "/datasets/item/item_data_quantity/saveItemQuantityDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        ItemData itemData = prepareTestItemData();
        ItemDataQuantity itemDataQuantity = ItemDataQuantity.builder()
                .id(1L)
                .itemDataSet(Set.of(itemData))
                .quantity(2)
                .build();
        return new EntityDataSet<>(itemDataQuantity, "/datasets/item/item_data_quantity/updateItemQuantityDataSet.yml");
    }

    private ItemData prepareTestItemData() {
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
                .fullPrice(price)
                .totalPrice(price)
                .build();

        itemData.addOptionItem(optionItem1);
        itemData.addOptionItem(optionItem2);

        return itemData;
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

        item.addReview(review);
    }

    private void addPosts(Item item) {
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .uniquePostId("1")
                .authorEmail("parent@email.com")
                .authorUserName("parent")
                .message("parent")
                .dateOfCreate(100000)
                .build();
        Post root = Post.builder()
                .id(2L)
                .title("root")
                .uniquePostId("2")
                .authorEmail("root@email.com")
                .authorUserName("root")
                .message("root")
                .dateOfCreate(100000)
                .build();
        Post child = Post.builder()
                .id(3L)
                .title("child")
                .uniquePostId("3")
                .authorEmail("child@email.com")
                .authorUserName("child")
                .message("child")
                .dateOfCreate(100000)
                .build();

        parent.addChildPost(root);
        root.addChildPost(child);

        item.addPost(parent);
        item.addPost(root);
        item.addPost(child);
    }
}