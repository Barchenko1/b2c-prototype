package com.b2c.prototype.dao.item.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.item.ArticularItem;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
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
import java.util.Set;
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

class BasicArticularItemDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ArticularItem.class, "articular_item"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicArticularItemDao(sessionFactory, queryService);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM articular_item_option_item");
            statement.execute("DELETE FROM option_item");
            statement.execute("DELETE FROM articular_item");

            statement.execute("ALTER SEQUENCE articular_item_id_seq RESTART WITH 1");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: articular_item", e);
        }
    }

    private ArticularItem prepareTestItemDataOption() {
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
                .value("L")
                .label("L")
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

        return ArticularItem.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .optionItems(Set.of(optionItem))
                .articularId("1")
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .build();
    }

    private ArticularItem prepareToSaveItemDataOption() {
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
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .value("M")
                .label("M")
                .build();
        optionGroup.addOptionItem(optionItem1);
        optionGroup.addOptionItem(optionItem2);
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
        ArticularStatus status = ArticularStatus.builder()
                .id(1L)
                .label("NEW")
                .value("NEW")
                .build();

        ArticularItem articularItem = ArticularItem.builder()
                .articularId("1")
                .dateOfCreate(10000)
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .status(status)
                .productName("Mob 1")
                .build();
        articularItem.addOptionItem(optionItem1);
        articularItem.addOptionItem(optionItem2);

        return articularItem;
    }

    private ArticularItem prepareToUpdateItemDataOption() {
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
        OptionItem optionItem1 = OptionItem.builder()
                .id(1L)
                .value("L")
                .label("L")
                .optionGroup(optionGroup)
                .build();
        OptionItem optionItem2 = OptionItem.builder()
                .id(2L)
                .value("M")
                .label("M")
                .optionGroup(optionGroup)
                .build();
        optionGroup.addOptionItem(optionItem1);
        optionGroup.addOptionItem(optionItem2);
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
        ArticularStatus status = ArticularStatus.builder()
                .id(2L)
                .label("COMMON")
                .value("COMMON")
                .build();

        ArticularItem articularItem = ArticularItem.builder()
                .id(1L)
                .articularId("1")
                .dateOfCreate(10000)
                .articularId("1")
                .fullPrice(price1)
                .discount(discount)
                .totalPrice(price2)
                .status(status)
                .productName("Mob 2")
                .build();

        articularItem.addOptionItem(optionItem1);
        articularItem.addOptionItem(optionItem2);
        return articularItem;
    }

    void checkItem(ArticularItem expectedArticularItem, ArticularItem actualArticularItem) {
        assertEquals(expectedArticularItem.getId(), actualArticularItem.getId());

        Set<OptionItem> actualOptionItem = actualArticularItem.getOptionItems();
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        Parameter parameter = new Parameter("id", 1L);
        ArticularItem articularItem = prepareTestItemDataOption();
        List<ArticularItem> resultList = dao.getNamedQueryEntityList("", parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkItem(articularItem, result);
        });
    }

    @Test
    void getEntityList_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntityList("", parameter);
        });
    }

    @Test
    void saveEntity_success() {
        loadDataSet("/datasets/item/articular_item/emptyArticularItem.yml");
        ArticularItem articularItem = prepareToSaveItemDataOption();

        dao.mergeEntity(articularItem);
        verifyExpectedData("/datasets/item/articular_item/saveArticularItem.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/item/articular_item/emptyArticularItem.yml");
        ArticularItem articularItem = prepareToSaveItemDataOption();
        dao.mergeEntity(articularItem);
        verifyExpectedData("/datasets/item/articular_item/saveArticularItem.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        ArticularItem articularItem = prepareToSaveItemDataOption();

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
        doThrow(new RuntimeException()).when(session).persist(articularItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(articularItem);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/item/articular_item/emptyArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            ArticularItem articularItem = prepareToSaveItemDataOption();
            s.merge(articularItem);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/articular_item/saveArticularItem.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/articular_item/emptyArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/item/articular_item/emptyArticularItem.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        Supplier<ArticularItem> itemSupplier = this::prepareToUpdateItemDataOption;
        dao.updateEntity(itemSupplier);
        verifyExpectedData("/datasets/item/articular_item/updateArticularItem.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<ArticularItem> itemSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(itemSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            ArticularItem itemToUpdate = prepareToUpdateItemDataOption();
            s.merge(itemToUpdate);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/articular_item/updateArticularItem.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
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
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/item/articular_item/deleteArticularItem.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        Consumer<Session> consumer = (Session s) -> {
            ArticularItem articularItem = prepareTestItemDataOption();
            s.remove(articularItem);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/item/articular_item/deleteArticularItem.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
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
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        ArticularItem articularItem = prepareTestItemDataOption();

        dao.deleteEntity(articularItem);
        verifyExpectedData("/datasets/item/articular_item/deleteArticularItem.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
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

        ArticularItem articularItem = prepareTestItemDataOption();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(articularItem);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        ArticularItem articularItem = prepareTestItemDataOption();
        Parameter parameter = new Parameter("id", 1L);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<ArticularItem> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
//        when(session.createNativeQuery(anyString(), eq(ArticularItem.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(articularItem);
        doThrow(new RuntimeException()).when(session).remove(articularItem);

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
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        Parameter parameter = new Parameter("id", 1L);
        ArticularItem articularItem = prepareTestItemDataOption();
        Optional<ArticularItem> resultOptional =
               dao.getNamedQueryOptionalEntity("", parameter);

        assertTrue(resultOptional.isPresent());
        ArticularItem result = resultOptional.get();
        checkItem(articularItem, result);
    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
           dao.getNamedQueryOptionalEntity("", parameter);
        });

    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/item/articular_item/testArticularItem.yml");
        Parameter parameter = new Parameter("id", 1L);

        ArticularItem articularItem = prepareTestItemDataOption();
        ArticularItem result = dao.getNamedQueryEntity("", parameter);

        checkItem(articularItem, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

}