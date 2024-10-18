package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.b2c.prototype.modal.entity.item.Rating;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.post.Post;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.modal.entity.review.Review;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.modal.GeneralEntity;
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
import java.util.Set;
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
            statement.execute("DELETE FROM item_post");
            statement.execute("DELETE FROM item_option");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable brandEntityTable = new EntityTable(Brand.class, "brand");
        EntityTable categoryEntityTable = new EntityTable(Category.class, "category");
        EntityTable discountEntityTable = new EntityTable(CurrencyDiscount.class, "currency_discount");
        EntityTable itemStatusEntityTable = new EntityTable(ItemStatus.class, "item_status");
        EntityTable itemTypeEntityTable = new EntityTable(ItemType.class, "item_type");
        EntityTable optionGroupEntityTable = new EntityTable(OptionGroup.class, "option_group");
        EntityTable optionItemEntityTable = new EntityTable(CurrencyDiscount.class, "option_item");
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
        Rating rating1 = Rating.builder().build();
        Rating rating2 = Rating.builder().build();
        Rating rating3 = Rating.builder().build();

        Review review = Review.builder()
                .title("review")
                .message("message")
                .dateOfCreate(100)
                .ratings(List.of(rating1, rating2, rating3))
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

    private Item prepareToSaveItem() {
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
                .amount(10)
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
        Item item = Item.builder()
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

        item.addOptionItem(optionItem1);
        item.addOptionItem(optionItem2);
        addPosts(item);

        return item;
    }

    private Item prepareTestItem() {
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

        Item item = Item.builder()
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

        item.addOptionItem(optionItem1);
        item.addOptionItem(optionItem2);
        addPosts(item);

        return item;
    }

    private Item prepareToUpdateItem() {
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
        PercentDiscount percentDiscount = PercentDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
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

        Item item = Item.builder()
                .id(1L)
                .name("Update name")
                .articularId("200")
                .dateOfCreate(200L)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .percentDiscount(percentDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .price(price)
                .build();

        item.addOptionItem(optionItem1);
        item.addOptionItem(optionItem2);
        addPosts(item);

        return item;
    }

    void checkItem(Item expectedItem, Item actualItem) {
        assertEquals(expectedItem.getId(), actualItem.getId());
        assertEquals(expectedItem.getBrand().getId(), actualItem.getBrand().getId());
        assertEquals(expectedItem.getBrand().getName(), actualItem.getBrand().getName());

        assertEquals(expectedItem.getCategory().getParent().getParent().getId(), actualItem.getCategory().getParent().getParent().getId());
        assertEquals(expectedItem.getCategory().getParent().getParent().getName(), actualItem.getCategory().getParent().getParent().getName());
        assertEquals(expectedItem.getCategory().getParent().getId(), actualItem.getCategory().getParent().getId());
        assertEquals(expectedItem.getCategory().getParent().getName(), actualItem.getCategory().getParent().getName());
        assertEquals(expectedItem.getCategory().getId(), actualItem.getCategory().getId());
        assertEquals(expectedItem.getCategory().getName(), actualItem.getCategory().getName());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Item item = prepareTestItem();
        List<Item> resultList = dao.getGeneralEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkItem(item, result);
        });
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Item item = prepareTestItem();
        List<Item> resultList = dao.getGeneralEntityList(Item.class, parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            assertEquals(item.getId(), result.getId());
            assertEquals(item.getBrand().getId(), result.getBrand().getId());
            assertEquals(item.getBrand().getName(), result.getBrand().getName());
        });
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntityList(parameter);
        });
    }

    @Test
    void getEntityListWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntityList(Object.class, parameter);
        });
    }

    @Test
    void saveEntity_success() {
        loadDataSet("/datasets/item/item/emptyItemDataSet.yml");
        Item item = prepareToSaveItem();


        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, item);

        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/item/item/saveItemDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item/emptyItemDataSet.yml");
        Item item = prepareToSaveItem();
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, item);
        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/item/item/saveItemDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        Item item = prepareToSaveItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, item);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).persist(item);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/item/item/emptyItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Item item = prepareToSaveItem();
            s.persist(item);
        };

        dao.saveGeneralEntity(consumer);
        verifyExpectedData("/datasets/item/item/saveItemDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item/emptyItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/item/item/emptyItemDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Supplier<Item> itemSupplier = this::prepareToUpdateItem;
        dao.updateGeneralEntity(itemSupplier);
        verifyExpectedData("/datasets/item/item/updateItemDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<Item> itemSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(itemSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Item itemToUpdate = prepareToUpdateItem();
            s.merge(itemToUpdate);
        };
        dao.updateGeneralEntity(consumer);
        verifyExpectedData("/datasets/item/item/updateItemDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Consumer<Session> itemConsumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(itemConsumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(parameter);
        verifyExpectedData("/datasets/item/item/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Item item = prepareTestItem();
            s.remove(item);
        };

        dao.deleteGeneralEntity(consumer);
        verifyExpectedData("/datasets/item/item/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntityByGeneralEntity_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Item item = prepareTestItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, item);

        dao.deleteGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/item/item/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        Item payment = prepareTestItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, payment);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(Item.class, parameter);
        verifyExpectedData("/datasets/item/item/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntity_transactionFailure() {
        Item item = new Item();

        Parameter parameter = new Parameter("id", 1L);

        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(transaction).commit();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, item);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Item item = new Item();

        Parameter parameter = new Parameter("id", 1L);

        IThreadLocalSessionManager sessionManager = mock(IThreadLocalSessionManager.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);

        try {
            Field sessionManagerField = AbstractGeneralEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, item);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(Item.class, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Item item = prepareTestItem();
        Optional<Item> resultOptional =
                dao.getOptionalGeneralEntity(parameter);

        assertTrue(resultOptional.isPresent());
        Item result = resultOptional.get();
        checkItem(item, result);
    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(parameter);
        });

    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Item item = prepareTestItem();

        Optional<Item> resultOptional =
                dao.getOptionalGeneralEntity(Item.class, parameter);

        assertTrue(resultOptional.isPresent());
        Item result = resultOptional.get();

        checkItem(item, result);
    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_Failure() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(Item.class, parameter);
        });
    }

    @Test
    void getOptionalEntityWithDependencies_OptionEmpty() {
        Parameter parameter = new Parameter("id", 100L);

        Optional<Item> result =
                dao.getOptionalGeneralEntity(Item.class, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Item item = prepareTestItem();
        Item result = dao.getGeneralEntity(parameter);

        checkItem(item, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(parameter);
        });
    }


    @Test
    void getEntityWithDependenciesWithClass_success() {
        loadDataSet("/datasets/item/item/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Item item = prepareTestItem();
        Item result = dao.getGeneralEntity(Item.class, parameter);

        checkItem(item, result);
    }

    @Test
    void getEntityWithDependenciesWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(Item.class, parameter);
        });
    }

}