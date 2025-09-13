package com.b2c.prototype.dao.item;

import com.b2c.prototype.dao.AbstractDaoTest;
import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.MetaData;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.review.Rating;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.review.Review;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import com.nimbusds.jose.util.Pair;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static com.b2c.prototype.util.Converter.getLocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemDaoTest extends AbstractDaoTest {

    @Autowired
    private IGeneralEntityDao generalEntityDao;

    @Test
    @DataSet(value = "datasets/item/item/emptyItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/item/saveItemDataSet.yml", orderBy = "id")
    public void persistEntity_success() {
        Item entity = getItem();
        entity.setId(0);

        generalEntityDao.persistEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/item/testItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/item/updateItemDataSet.yml", orderBy = "id")
    public void mergeEntity_success() {
        Item entity = getItem();
//        entity.setValue("Update New");

        generalEntityDao.mergeEntity(entity);
    }

    @Test
    @DataSet(value = "datasets/item/item/testItemDataSet.yml", cleanBefore = true)
    @ExpectedDataSet(value = "datasets/item/item/emptyItemDataSet.yml", orderBy = "id")
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

    private void addReview(Item item) {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();

        Review review = Review.builder()
                .id(1L)
                .title("title")
                .message("message")
                .dateOfCreate(getLocalDateTime("2024-03-03 12:00:00"))
                .rating(rating)
                .build();

//        item.addReview(review);
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

//        item.addPost(parent);
//        item.addPost(root);
//        item.addPost(child);
    }

    private Item getItem() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = getCategory();
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

        MetaData itemData = MetaData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();
        ArticularItem articularItem = ArticularItem.builder()

                .build();

        Item item = Item.builder()
                .id(1L)
                .articularItem(articularItem)
                .build();
        addReview(item);
        addPosts(item);

        return item;
    }

}