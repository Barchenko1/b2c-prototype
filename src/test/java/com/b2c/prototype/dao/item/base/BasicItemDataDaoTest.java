package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.price.Currency;
import com.tm.core.process.dao.common.AbstractEntityDao;
import com.tm.core.process.dao.identifier.QueryService;
import com.tm.core.finder.manager.EntityMappingManager;
import com.tm.core.finder.manager.IEntityMappingManager;
import com.tm.core.finder.parameter.Parameter;
import com.tm.core.finder.table.EntityTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasicItemDataDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemData.class, "item_data"));
queryService = new QueryService(entityMappingManager);
        dao = new BasicItemDataDao(sessionFactory, queryService);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM articular_item");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: articular_item", e);
        }
    }

    private Category prepareCategories() {
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

        parent.setChildNodeList(List.of(root));
        root.setParent(parent);
        root.setChildNodeList(List.of(child));
        child.setParent(root);

        return child;
    }

    private ItemData prepareToItemData() {
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

        return itemData;
    }

    private ItemData prepareToSaveItemData() {
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
                .itemId("123")
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();

        return itemData;
    }

    private ItemData prepareToUpdateItemData() {
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
                .itemId("123")
                .category(category)
                .brand(brand)
                .itemType(itemType)
                .build();

        return itemData;
    }

    private void checkItemData(ItemData expectedItemData, ItemData actualItemData) {
        assertEquals(expectedItemData.getId(), actualItemData.getId());
//        assertEquals(expectedItemData.getFullPrice(), actualItemData.getFullPrice());
//        assertEquals(expectedItemData.getTotalPrice(), actualItemData.getTotalPrice());

//        assertEquals(expectedItemData.getCategory().getName(), actualItemData.getCategory().getName());
//        assertEquals(expectedItemData.getItemType().getValue(), actualItemData.getItemType().getValue());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ItemData itemData = prepareToItemData();
        List<ItemData> resultList = dao.getEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkItemData(itemData, result);
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
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item_data/emptyItemDataSet.yml");
        ItemData itemData = prepareToSaveItemData();
        dao.mergeEntity(itemData);
        verifyExpectedData("/datasets/item/item_data/saveItemDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        ItemData itemData = prepareToSaveItemData();
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
        doThrow(new RuntimeException()).when(session).persist(itemData);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(itemData);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/item/item_data/emptyItemDataSet.yml");
        Consumer<Session> consumer = (Session session) -> {
            ItemData itemData = prepareToSaveItemData();
            session.merge(itemData);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item_data/saveItemDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data/emptyItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/item/item_data/emptyItemDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Supplier<ItemData> itemSupplier = this::prepareToUpdateItemData;
        dao.updateEntity(itemSupplier);
        verifyExpectedData("/datasets/item/item_data/updateItemDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<ItemData> itemSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(itemSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ItemData itemDataToUpdate = prepareToUpdateItemData();
            s.merge(itemDataToUpdate);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item_data/updateItemDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
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
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/item/item_data/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ItemData itemData = prepareToItemData();
            s.remove(itemData);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item_data/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
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
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        ItemData itemData = prepareToItemData();

        dao.deleteEntity(itemData);
        verifyExpectedData("/datasets/item/item_data/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
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

        ItemData itemData = prepareToItemData();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(itemData);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        ItemData itemData = new ItemData();

        Parameter parameter = new Parameter("id", 1L);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<ItemData> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        when(session.createNativeQuery(anyString(), eq(ItemData.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(itemData);
        doThrow(new RuntimeException()).when(session).remove(itemData);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
        verify(transaction).rollback();
        verify(transaction, never()).commit();
        verify(session).close();
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ItemData itemData = prepareToItemData();
        Optional<ItemData> resultOptional =
                dao.getOptionalEntity(parameter);

        assertTrue(resultOptional.isPresent());
        ItemData result = resultOptional.get();
        checkItemData(itemData, result);
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
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        ItemData itemData = prepareToItemData();
        ItemData result = dao.getEntity(parameter);

        checkItemData(itemData, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });
    }

}