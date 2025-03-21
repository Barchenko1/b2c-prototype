package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
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

class BasicOptionItemDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    public static void setup() {
        queryService = new QueryService(getEntityMappingManager());
        dao = new BasicOptionItemDao(sessionFactory, queryService);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            statement.execute("DELETE FROM item");
            statement.execute("DELETE FROM articular_item");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable optionGroupEntityTable = new EntityTable(OptionGroup.class, "option_group");
        EntityTable optionItemEntityTable = new EntityTable(OptionItem.class, "option_item");
        EntityTable itemEntityTable = new EntityTable(Item.class, "item");

        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(optionGroupEntityTable);
        entityMappingManager.addEntityTable(optionItemEntityTable);
        entityMappingManager.addEntityTable(itemEntityTable);
        return entityMappingManager;
    }

    public OptionItem prepareToSaveOptionItem() {
        OptionItem optionItem = OptionItem.builder()
                .value("L")
                .label("L")
                .build();
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();
        optionGroup.addOptionItem(optionItem);
        return optionGroup.getOptionItems().get(0);
    }

    public OptionItem prepareTestOptionItem() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .build();

        return OptionItem.builder()
                .id(1L)
                .optionGroup(optionGroup)
                .value("L")
                .label("L")
                .build();
    }

    public OptionItem prepareToUpdateOptionItem() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .value("Size")
                .label("Size")
                .build();

        OptionItem optionItem = OptionItem.builder()
                .id(1L)
                .value("XL")
                .label("XL")
                .build();
        optionGroup.addOptionItem(optionItem);
        return optionGroup.getOptionItems().get(0);
    }

    private void checkOptionItem(OptionItem expectedOptionItem, OptionItem actualOptionItem) {
        assertEquals(expectedOptionItem.getId(), actualOptionItem.getId());
        assertEquals(expectedOptionItem.getValue(), actualOptionItem.getValue());

//        assertEquals(expectedOptionItem.getOptionGroup().getId(), actualOptionItem.getOptionGroup().getId());
//        assertEquals(expectedOptionItem.getOptionGroup().getValue(), actualOptionItem.getOptionGroup().getValue());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OptionItem optionItem = prepareTestOptionItem();
        List<OptionItem> resultList =
                dao.getNamedQueryEntityList("", parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkOptionItem(optionItem, result));
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
        loadDataSet("/datasets/option/option_item/emptyOptionItemWithOptionGroupDataSet.yml");
        OptionItem optionItem = prepareToSaveOptionItem();

        dao.mergeEntity(optionItem);
        verifyExpectedData("/datasets/option/option_item/saveOptionItemDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        OptionItem optionItem = new OptionItem();
        optionItem.setId(1L);
        optionItem.setValue("Size");

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
        doThrow(new RuntimeException()).when(session).persist(optionItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(optionItem);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/option/option_item/emptyOptionItemWithOptionGroupDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OptionItem optionItem = prepareToSaveOptionItem();
            s.merge(optionItem);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/option/option_item/saveOptionItemDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/option/option_item/emptyOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Supplier<OptionItem> optionItemSupplier = this::prepareToUpdateOptionItem;
        dao.updateEntity(optionItemSupplier);
        verifyExpectedData("/datasets/option/option_item/updateOptionItemDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<OptionItem> optionItemSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(optionItemSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OptionItem optionItemToUpdate = prepareToUpdateOptionItem();

//            s.merge(optionItemToUpdate.getOptionGroup());
            s.merge(optionItemToUpdate);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/option/option_item/updateOptionItemDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Consumer<Session> optionItemConsumer = (Session s) -> {
            OptionItem optionItem = prepareToUpdateOptionItem();
            optionItem.setId(0);
            s.merge(optionItem);
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(optionItemConsumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemWithOptionGroupDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OptionItem optionItem = prepareTestOptionItem();
//            s.remove(optionItem.getOptionGroup());
            s.remove(optionItem);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemWithOptionGroupDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntityEntity_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        OptionItem optionItem = prepareTestOptionItem();

        dao.deleteEntity(optionItem);
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemWithOptionGroupDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
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

        OptionItem optionItem = prepareTestOptionItem();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(optionItem);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/item/item/testItemSet.yml");
        OptionItem optionItem = new OptionItem();

        Parameter parameter = new Parameter("id", 1L);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<OptionItem> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
//        when(session.createNativeQuery(anyString(), eq(OptionItem.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(optionItem);
        doThrow(new RuntimeException()).when(session).remove(optionItem);

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
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OptionItem optionItem = prepareTestOptionItem();
        Optional<OptionItem> resultOptional =
               dao.getNamedQueryOptionalEntity("", parameter);

        assertTrue(resultOptional.isPresent());
        OptionItem result = resultOptional.get();

        checkOptionItem(optionItem, result);
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
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OptionItem optionItem = prepareTestOptionItem();

        OptionItem result = dao.getNamedQueryEntity("", parameter);

        checkOptionItem(optionItem, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

}