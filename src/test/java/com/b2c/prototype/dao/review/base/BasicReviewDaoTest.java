package com.b2c.prototype.dao.review.base;

import com.b2c.prototype.dao.AbstractConstantEntityDaoTest;
import com.b2c.prototype.dao.EntityDataSet;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.review.Review;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.table.EntityTable;
import org.junit.jupiter.api.BeforeAll;

import java.util.List;

class BasicReviewDaoTest extends AbstractConstantEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Review.class, "review"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicReviewDao(sessionFactory, queryService);
    }

    @Override
    protected String getEmptyDataSetPath() {
        return "/datasets/review/review/emptyReviewDataSet.yml";
    }

    @Override
    protected EntityDataSet<?> getTestDataSet() {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();
        Review review = Review.builder()
                .id(1L)
                .dateOfCreate(100)
                .title("title")
                .message("message")
                .rating(rating)
                .comments(List.of())
                .build();
        return new EntityDataSet<>(review, "/datasets/review/review/testReviewDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getSaveDataSet() {
        Rating rating = Rating.builder()
                .id(5L)
                .value(5)
                .build();
        Review review = Review.builder()
                .dateOfCreate(100)
                .title("title")
                .message("message")
                .rating(rating)
                .comments(List.of())
                .build();
        return new EntityDataSet<>(review, "/datasets/review/review/saveReviewDataSet.yml");
    }

    @Override
    protected EntityDataSet<?> getUpdateDataSet() {
        Rating rating = Rating.builder()
                .id(3L)
                .value(3)
                .build();
        Review review = Review.builder()
                .id(1L)
                .uniqueId("123")
                .dateOfCreate(200)
                .title("Update title")
                .message("Update message")
                .rating(rating)
                .comments(List.of())
                .build();
        return new EntityDataSet<>(review, "/datasets/review/review/updateReviewDataSet.yml");
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
    }

    private Item preparItem() {
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

        ItemData itemData = ItemData.builder()
                .id(1L)
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();

        Item item = Item.builder()
                .id(1L)
                .build();

        addPosts(item);

        return item;
    }
}