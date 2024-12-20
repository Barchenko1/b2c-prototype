package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Brand;
import com.b2c.prototype.modal.entity.item.Category;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.item.ItemData;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.modal.entity.item.PercentDiscount;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.processor.finder.manager.EntityMappingManager;
import com.tm.core.processor.finder.manager.IEntityMappingManager;
import com.tm.core.processor.finder.parameter.Parameter;
import com.tm.core.processor.finder.table.EntityTable;
import com.tm.core.processor.thread.IThreadLocalSessionManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BasicItemDataDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemData.class, "item_data"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicItemDataDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_data_option");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
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

    private ItemData prepareToItemData() {
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
        PercentDiscount percentDiscount = PercentDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
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
                .percentDiscount(percentDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .totalPrice(price)
                .fullPrice(price)
                .build();

        itemData.addOptionItem(optionItem1);
        itemData.addOptionItem(optionItem2);

        return itemData;
    }

    private ItemData prepareToSaveItemData() {
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
        PercentDiscount percentDiscount = PercentDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
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
                .name("name")
                .articularId("100")
                .dateOfCreate(100L)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .percentDiscount(percentDiscount)
//                .optionItemSet(Set.of(optionItem1, optionItem2))
                .status(itemStatus)
                .itemType(itemType)
                .totalPrice(price)
                .fullPrice(price)
                .build();

        itemData.addOptionItem(optionItem1);
        itemData.addOptionItem(optionItem2);

        return itemData;
    }

    private ItemData prepareToUpdateItemData() {
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
        PercentDiscount percentDiscount = PercentDiscount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
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
                .name("Update name")
                .articularId("200")
                .dateOfCreate(200L)
                .category(category)
                .brand(brand)
                .currencyDiscount(currencyDiscount)
                .percentDiscount(percentDiscount)
                .status(itemStatus)
                .itemType(itemType)
                .totalPrice(price)
                .fullPrice(price)
                .build();

        itemData.addOptionItem(optionItem1);
        itemData.addOptionItem(optionItem2);

        return itemData;
    }

    private void checkItemData(ItemData expectedItemData, ItemData actualItemData) {
        assertEquals(expectedItemData.getId(), actualItemData.getId());
        assertEquals(expectedItemData.getName(), actualItemData.getName());
        assertEquals(expectedItemData.getArticularId(), actualItemData.getArticularId());
        assertEquals(expectedItemData.getDateOfCreate(), actualItemData.getDateOfCreate());
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

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
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

        dao.saveEntity(consumer);
        verifyExpectedData("/datasets/item/item_data/saveItemDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data/emptyItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveEntity(consumer);
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
        dao.updateEntity(consumer);
        verifyExpectedData("/datasets/item/item_data/updateItemDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Consumer<Session> itemConsumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(itemConsumer);
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

        dao.deleteEntity(consumer);
        verifyExpectedData("/datasets/item/item_data/emptyItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data/testItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(consumer);
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