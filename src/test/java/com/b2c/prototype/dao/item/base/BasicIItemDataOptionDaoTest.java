package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.item.ItemDataOption;
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

class BasicIItemDataOptionDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ItemDataOption.class, "item_data_option"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicIItemDataOptionDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item_data_option");
            statement.execute("DELETE FROM option_item");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_data_option", e);
        }
    }

    private ItemDataOption prepareTestItemDataOption() {
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
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
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
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();

        return ItemDataOption.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .optionItem(optionItem)
                .articularId("1")
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

    private ItemDataOption prepareToSaveItemDataOption() {
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
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
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
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();

        return ItemDataOption.builder()
                .articularId("1")
                .dateOfCreate(10000)
                .optionItem(optionItem)
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

    private ItemDataOption prepareToUpdateItemDataOption() {
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
        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .optionName("L")
                .optionGroup(optionGroup)
                .build();
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
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
                .currency(currency)
                .build();

        return ItemDataOption.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .optionItem(optionItem)
                .articularId("1")
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

    void checkItem(ItemDataOption expectedItemDataOption, ItemDataOption actualItemDataOption) {
        assertEquals(expectedItemDataOption.getId(), actualItemDataOption.getId());

        OptionItem actualOptionItem = actualItemDataOption.getOptionItem();
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ItemDataOption itemDataOption = prepareTestItemDataOption();
        List<ItemDataOption> resultList = dao.getEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkItem(itemDataOption, result);
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
        loadDataSet("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
        ItemDataOption itemDataOption = prepareToSaveItemDataOption();

        dao.mergeEntity(itemDataOption);
        verifyExpectedData("/datasets/item/item_data_option/saveItemDataOptionDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
        ItemDataOption itemDataOption = prepareToSaveItemDataOption();
        dao.mergeEntity(itemDataOption);
        verifyExpectedData("/datasets/item/item_data_option/saveItemDataOptionDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        ItemDataOption itemDataOption = prepareToSaveItemDataOption();

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
        doThrow(new RuntimeException()).when(session).persist(itemDataOption);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(itemDataOption);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ItemDataOption itemDataOption = prepareToSaveItemDataOption();
            s.merge(itemDataOption);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item_data_option/saveItemDataOptionDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        Supplier<ItemDataOption> itemSupplier = this::prepareToUpdateItemDataOption;
        dao.updateEntity(itemSupplier);
        verifyExpectedData("/datasets/item/item_data_option/updateItemDataOptionDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<ItemDataOption> itemSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(itemSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ItemDataOption itemToUpdate = prepareToUpdateItemDataOption();
            s.merge(itemToUpdate);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item_data_option/updateItemDataOptionDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
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
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ItemDataOption itemDataOption = prepareTestItemDataOption();
            s.remove(itemDataOption);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
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
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        ItemDataOption itemDataOption = prepareTestItemDataOption();

        dao.deleteEntity(itemDataOption);
        verifyExpectedData("/datasets/item/item_data_option/emptyItemDataOptionDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
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

        ItemDataOption itemDataOption = prepareTestItemDataOption();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(itemDataOption);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");

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
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ItemDataOption itemDataOption = prepareTestItemDataOption();
        Optional<ItemDataOption> resultOptional =
                dao.getOptionalEntity(parameter);

        assertTrue(resultOptional.isPresent());
        ItemDataOption result = resultOptional.get();
        checkItem(itemDataOption, result);
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
        loadDataSet("/datasets/item/item_data_option/testItemDataOptionDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        ItemDataOption itemDataOption = prepareTestItemDataOption();
        ItemDataOption result = dao.getEntity(parameter);

        checkItem(itemDataOption, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });
    }

}