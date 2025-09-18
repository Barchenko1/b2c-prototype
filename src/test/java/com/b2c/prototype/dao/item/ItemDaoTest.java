package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/item/item/emptyItemDataSet.yml", cleanBefore = true,
    executeStatementsBefore = {
            "TRUNCATE TABLE option_item RESTART IDENTITY CASCADE",
    })
    @ExpectedDataSet(value = "datasets/item/item/saveItemDataSet.yml", orderBy = "id", ignoreCols = {"label", "value", "id", "message", "title", "post_id", "post_uniq_id", "category_id", "option_group_id", "brand_id", "discount_id", "full_price_id", "total_price_id", "status_id"})
    public void persistEntity_success() {
        Item entity = getItem();
        entity.setId(0);
        entity.getMetaData().setId(0);
        entity.getMetaData().getBrand().setId(0);
        entity.getMetaData().getItemType().setId(0);
        entity.getMetaData().getCategory().setId(0);
        entity.getArticularItems().forEach(articularItem -> {
            articularItem.setId(0);

            articularItem.getStatus().setId(0);

            articularItem.getDiscount().setId(0);
            articularItem.getFullPrice().setId(0);
            articularItem.getTotalPrice().setId(0);

            articularItem.getOptionItems().forEach(optionItem -> {
                optionItem.setId(0);
                optionItem.getOptionGroup().setId(0);
            });

            articularItem.getStatus().setId(0);
        });
        entity.getPosts().forEach(post -> {
            post.setId(0);
        });
        entity.getReviews().forEach(review -> {
            review.setId(0);
        });

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/item/testItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/item/updateItemDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Item entity = getItem();
        entity.setItemUniqId("Update 123");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/item/testItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/item/removeItemDataSet.yml", orderBy = "id")
    public void removeEntity_success() {
        Item entity = getItem();

        generalEntityDao.removeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/item/testItemDataSet.yml", cleanBefore = true)
    public void findEntity_success() {
        Item expected = getItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Item entity = generalEntityDao.findEntity("Item.findById", List.of(pair));

        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/item/testItemDataSet.yml", cleanBefore = true)
    public void findOptionEntity_success() {
        Item expected = getItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        Optional<Item> optionEntity = generalEntityDao.findOptionEntity("Item.findById", List.of(pair));

        assertTrue(optionEntity.isPresent());

        Item entity = optionEntity.get();
        assertEquals(expected, entity);
    }

    @Test
    @DataSet(value = "datasets/item/item/testItemDataSet.yml", cleanBefore = true)
    public void findEntityList_success() {
        Item entity = getItem();

        Pair<String, Long> pair = Pair.of("id", 1L);
        List<Item> entityList = generalEntityDao.findEntityList("Item.findById", List.of(pair));

        assertEquals(List.of(entity), entityList);
    }

    private Category getCategory() {
        return Category.builder()
                .id(1L)
                .label("category")
                .value("category")
                .build();
    }

    private MetaData getMetaData() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = getCategory();
        ItemType itemType = ItemType.builder()
                .id(1L)
                .value("Clothes")
                .label("Clothes")
                .build();

        Map<String, String> description = new HashMap<>(){{
            put("desc1", "desc1");
            put("desc2", "desc2");
        }};

        return MetaData.builder()
                .id(1L)
                .metadataUniqId("123")
                .description(description)
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();
    }

    private ArticularItem getArticularItem() {
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
        OptionItem optionItemL = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .build();
        OptionItem optionItemM = OptionItem.builder()
                .id(2L)
                .value("M")
                .label("M")
                .build();

        optionGroup.addOptionItem(optionItemL);
        optionGroup.addOptionItem(optionItemM);
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
                .amount(10)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();
        ArticularStatus articularStatus = ArticularStatus.builder()
                .id(1L)
                .label("NEW")
                .value("NEW")
                .build();

        return ArticularItem.builder()
                .id(1L)
                .articularUniqId("123")
                .productName("Mob 1")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .optionItems(Set.of(optionItemL, optionItemM))
                .status(articularStatus)
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

    private void addReview(Item item) {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();

        Review review = Review.builder()
                .id(1L)
                .reviewUniqId("123")
                .title("title")
                .message("message")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .rating(rating)
                .build();

        item.addReview(review);
    }

    private void addPosts(Item item) {
        Post parent = Post.builder()
                .id(1L)
                .title("parent")
                .postUniqId("1")
                .message("parent")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
        Post root = Post.builder()
                .id(2L)
                .title("root")
                .postUniqId("2")
                .message("root")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();
        Post child = Post.builder()
                .id(3L)
                .title("child")
                .postUniqId("3")
                .message("child")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .build();

        parent.addChildPost(root);
        root.addChildPost(child);

        item.addPost(parent);
        item.addPost(root);
        item.addPost(child);
    }

    private Item getItem() {
        MetaData metaData = getMetaData();
        ArticularItem articularItem = getArticularItem();

        Item item = Item.builder()
                .id(1L)
                .itemUniqId("123")
                .metaData(metaData)
                .articularItems(List.of(articularItem))
                .build();
        addReview(item);
        addPosts(item);

        return item;
    }

}