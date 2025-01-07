package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.Discount;
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
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import com.tm.core.processor.thread.ThreadLocalSessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicItemDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, getEntityMappingManager());
        dao = new BasicItemDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_review");
            statement.execute("DELETE FROM item_post");
            statement.execute("DELETE FROM item_data_option");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable brandEntityTable = new EntityTable(Brand.class, "brand");
        EntityTable categoryEntityTable = new EntityTable(Category.class, "category");
        EntityTable discountEntityTable = new EntityTable(Discount.class, "discount");
        EntityTable itemStatusEntityTable = new EntityTable(ItemStatus.class, "item_status");
        EntityTable itemTypeEntityTable = new EntityTable(ItemType.class, "item_type");
        EntityTable optionGroupEntityTable = new EntityTable(OptionGroup.class, "option_group");
        EntityTable optionItemEntityTable = new EntityTable(OptionItem.class, "option_item");
        EntityTable currencyEntityTable = new EntityTable(Currency.class, "currency");
        EntityTable priceEntityTable = new EntityTable(Price.class, "price");
        EntityTable ratingEntityTable = new EntityTable(Rating.class, "rating");
        EntityTable reviewEntityTable = new EntityTable(ItemStatus.class, "review");
        EntityTable postEntityTable = new EntityTable(ItemType.class, "post");

        EntityTable itemEntityTable = new EntityTable(Item.class, "item");

        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(brandEntityTable);
        entityMappingManager.addEntityTable(categoryEntityTable);
        entityMappingManager.addEntityTable(discountEntityTable);
        entityMappingManager.addEntityTable(itemStatusEntityTable);
        entityMappingManager.addEntityTable(itemTypeEntityTable);
        entityMappingManager.addEntityTable(optionGroupEntityTable);
        entityMappingManager.addEntityTable(optionItemEntityTable);
        entityMappingManager.addEntityTable(currencyEntityTable);
        entityMappingManager.addEntityTable(priceEntityTable);
        entityMappingManager.addEntityTable(ratingEntityTable);
        entityMappingManager.addEntityTable(reviewEntityTable);
        entityMappingManager.addEntityTable(postEntityTable);

        entityMappingManager.addEntityTable(itemEntityTable);
        return entityMappingManager;
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

    private Item prepareToSaveItem() {
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
        ItemStatus itemStatus = ItemStatus.builder()
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
                .status(itemStatus)
                .itemType(itemType)
                .build();

        Item item = Item.builder()
                .itemData(itemData)
                .build();
        addReview(item);
        addPosts(item);

        return item;
    }

    private Item prepareTestItem() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = prepareCategories();
        ItemStatus itemStatus = ItemStatus.builder()
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
                .status(itemStatus)
                .itemType(itemType)
                .build();

        Item item = Item.builder()
                .id(1L)
                .itemData(itemData)
                .build();
        addReview(item);
        addPosts(item);

        return item;
    }

    private Item prepareToUpdateItem() {
        Brand brand = Brand.builder()
                .id(1L)
                .value("Hermes")
                .label("Hermes")
                .build();
        Category category = prepareCategories();
        ItemStatus itemStatus = ItemStatus.builder()
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
                .itemId("123")
                .brand(brand)
                .status(itemStatus)
                .itemType(itemType)
                .build();

        Item item = Item.builder()
                .id(1L)
                .itemData(itemData)
                .build();
        addReview(item);
        addPosts(item);

        return item;
    }

    void checkItem(Item expectedItem, Item actualItem) {
        assertEquals(expectedItem.getId(), actualItem.getId());

        ItemData actualItemData = actualItem.getItemData();
//        assertEquals(expectedItem.getItemData().getId(), actualItemData.getId());
//        assertEquals(expectedItem.getItemData().getName(), actualItemData.getName());
//        assertEquals(expectedItem.getItemData().getArticularId(), actualItemData.getArticularId());
//        assertEquals(expectedItem.getItemData().getDateOfCreate(), actualItemData.getDateOfCreate());

//        Brand brand = actualItemData.getBrand();
//        assertEquals(expectedItem.getItemData().getBrand().getId(), brand.getId());
//        assertEquals(expectedItem.getItemData().getBrand().getName(), brand.getName());
//
//        Category category = actualItemData.getCategory();
//        assertEquals(expectedItem.getItemData().getCategory().getParent().getParent().getId(), category.getParent().getParent().getId());
//        assertEquals(expectedItem.getItemData().getCategory().getParent().getParent().getName(), category.getParent().getParent().getName());
//        assertEquals(expectedItem.getItemData().getCategory().getParent().getId(), category.getParent().getId());
//        assertEquals(expectedItem.getItemData().getCategory().getParent().getName(), category.getParent().getName());
//        assertEquals(expectedItem.getItemData().getCategory().getId(), category.getId());
//        assertEquals(expectedItem.getItemData().getCategory().getName(), category.getName());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Item item = prepareTestItem();
        List<Item> resultList = dao.getEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkItem(item, result);
        });
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntityList(parameter);
        });
    }

    @Test
    void saveEntity_success() {
        loadDataSet("/datasets/item/item/emptyItemSet.yml");
        Item item = prepareToSaveItem();

        dao.persistEntity(item);
        verifyExpectedData("/datasets/item/item/saveItemSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item/emptyItemSet.yml");
        Item item = prepareToSaveItem();
        dao.persistEntity(item);
        verifyExpectedData("/datasets/item/item/saveItemSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        Item item = prepareToSaveItem();

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).persist(item);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(item);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/item/item/emptyItemSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Item item = prepareToSaveItem();
            s.persist(item);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item/saveItemSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item/emptyItemSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/item/item/emptyItemSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Supplier<Item> itemSupplier = this::prepareToUpdateItem;
        dao.updateEntity(itemSupplier);
        verifyExpectedData("/datasets/item/item/updateItemSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<Item> itemSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(itemSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Item itemToUpdate = prepareToUpdateItem();
            s.merge(itemToUpdate);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item/updateItemSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Consumer<Session> itemConsumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(itemConsumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/item/item/emptyItemSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Item item = prepareTestItem();
            s.remove(item);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item/emptyItemSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntityByGeneralEntity_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Item item = prepareTestItem();

        dao.deleteEntity(item);
        verifyExpectedData("/datasets/item/item/emptyItemSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        Item item = prepareTestItem();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(item);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Item item = new Item();

        Parameter parameter = new Parameter("id", 1L);

        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(transaction).commit();

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Item item = prepareTestItem();
        Optional<Item> resultOptional =
                dao.getOptionalEntity(parameter);

        assertTrue(resultOptional.isPresent());
        Item result = resultOptional.get();
        checkItem(item, result);
    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getOptionalEntity(parameter);
        });

    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Item item = prepareTestItem();
        Item result = dao.getEntity(parameter);

        checkItem(item, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });
    }

}