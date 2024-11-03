package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.dao.AbstractSingleEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
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

import java.util.List;
import java.util.Set;

class BasicReviewDaoTest extends AbstractSingleEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Review.class, "review"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicReviewDao(sessionFactory, entityIdentifierDao);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/review/emptyReviewDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();
        Item item = preparItem();
        Review review = Review.builder()
                .id(1L)
                .dateOfCreate(100)
                .title("title")
                .message("message")
                .rating(rating)
                .items(Set.of(item))
                .build();
        return new EntityDataSet<>(review, "/datasets/review/testReviewDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();
        Item item = preparItem();
        Review review = Review.builder()
                .dateOfCreate(100)
                .title("title")
                .message("message")
                .rating(rating)
                .items(Set.of(item))
                .build();
        return new EntityDataSet<>(review, "/datasets/review/saveReviewDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Rating rating = Rating.builder()
                .id(3L)
                .value(3)
                .build();
        Item item = preparItem();
        Review review = Review.builder()
                .id(1L)
                .dateOfCreate(200)
                .title("Update title")
                .message("Update message")
                .rating(rating)
                .items(Set.of(item))
                .build();
        return new EntityDataSet<>(review, "/datasets/review/updateReviewDataSet.yml");
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

    private Item preparItem() {
        Brand brand = Brand.builder()
                .id(1L)
                .name("Hermes")
                .build();
        Category category = prepareCategories();
        Currency currency = Currency.builder()
                .id(1L)
                .name("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .currency(currency)
                .build();
        ItemStatus itemStatus = ItemStatus.builder()
                .id(1L)
                .name("NEW")
                .build();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .name("Clothes")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .name("Size")
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

        Item item = Item.builder()
                .id(1L)
                .itemData(itemData)
                .build();

        addPosts(item);

        return item;
    }
}