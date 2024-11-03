package com.b2c.prototype.dao.option.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Item;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
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
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
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

class BasicOptionItemDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, getEntityMappingManager());
        dao = new BasicOptionItemDao(sessionFactory, entityIdentifierDao);
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
        OptionGroup optionGroup = OptionGroup.builder()
                .name("Size")
                .build();

        return OptionItem.builder()
                .optionGroup(optionGroup)
                .optionName("L")
                .build();
    }

    public OptionItem prepareTestOptionItem() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .name("Size")
                .build();

        return OptionItem.builder()
                .id(1L)
                .optionGroup(optionGroup)
                .optionName("L")
                .build();
    }

    public OptionItem prepareToUpdateOptionItem() {
        OptionGroup optionGroup = OptionGroup.builder()
                .id(1L)
                .name("Color")
                .build();

        return OptionItem.builder()
                .id(1L)
                .optionGroup(optionGroup)
                .optionName("Red")
                .build();
    }

    private void checkOptionItem(OptionItem expectedOptionItem, OptionItem actualOptionItem) {
        assertEquals(expectedOptionItem.getId(), actualOptionItem.getId());
        assertEquals(expectedOptionItem.getOptionName(), actualOptionItem.getOptionName());

        assertEquals(expectedOptionItem.getOptionGroup().getId(), actualOptionItem.getOptionGroup().getId());
        assertEquals(expectedOptionItem.getOptionGroup().getName(), actualOptionItem.getOptionGroup().getName());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OptionItem optionItem = prepareTestOptionItem();
        List<OptionItem> resultList =
                dao.getGeneralEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkOptionItem(optionItem, result));
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OptionItem optionItem = prepareTestOptionItem();
        List<OptionItem> resultList =
                dao.getGeneralEntityList(OptionItem.class, parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkOptionItem(optionItem, result));
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
        loadDataSet("/datasets/option/option_item/emptyOptionItemDataSet.yml");
        OptionItem optionItem = prepareToSaveOptionItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, optionItem.getOptionGroup());
        generalEntity.addEntityPriority(2, optionItem);

        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/option/option_item/saveOptionItemDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/option/option_item/emptyOptionItemDataSet.yml");
        OptionItem optionItem = prepareToSaveOptionItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, optionItem.getOptionGroup());
        generalEntity.addEntityPriority(2, optionItem);
        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/option/option_item/saveOptionItemDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        OptionItem optionItem = new OptionItem();
        optionItem.setId(1L);
        optionItem.setOptionName("Size");

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, optionItem);

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
        doThrow(new RuntimeException()).when(session).persist(optionItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/option/option_item/emptyOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OptionItem optionItem = prepareToSaveOptionItem();
            s.persist(optionItem.getOptionGroup());
            s.persist(optionItem);
        };

        dao.saveGeneralEntity(consumer);
        verifyExpectedData("/datasets/option/option_item/saveOptionItemDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/option/option_item/emptyOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Supplier<OptionItem> optionItemSupplier = this::prepareToUpdateOptionItem;
        dao.updateGeneralEntity(optionItemSupplier);
        verifyExpectedData("/datasets/option/option_item/updateOptionItemDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<OptionItem> optionItemSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(optionItemSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OptionItem optionItemToUpdate = prepareToUpdateOptionItem();

            s.merge(optionItemToUpdate.getOptionGroup());
            s.merge(optionItemToUpdate);
        };
        dao.updateGeneralEntity(consumer);
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
            dao.updateGeneralEntity(optionItemConsumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(parameter);
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemWithOptionGroupDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            OptionItem optionItem = prepareTestOptionItem();
            s.remove(optionItem.getOptionGroup());
            s.remove(optionItem);
        };

        dao.deleteGeneralEntity(consumer);
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
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
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        OptionItem optionItem = prepareTestOptionItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, optionItem.getOptionGroup());
        generalEntity.addEntityPriority(2, optionItem);

        dao.deleteGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
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

        OptionItem optionItem = prepareTestOptionItem();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, optionItem);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(OptionItem.class, parameter);
        verifyExpectedData("/datasets/option/option_item/emptyOptionItemWithOptionGroupDataSet.yml");
    }

    @Test
    void deleteEntity_transactionFailure() {
        OptionItem optionItem = new OptionItem();

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
        generalEntity.addEntityPriority(2, optionItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_transactionFailure() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        OptionItem optionItem = new OptionItem();

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
        generalEntity.addEntityPriority(2, optionItem);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(OptionItem.class, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        OptionItem optionItem = prepareTestOptionItem();
        Optional<OptionItem> resultOptional =
                dao.getOptionalGeneralEntity(parameter);

        assertTrue(resultOptional.isPresent());
        OptionItem result = resultOptional.get();

        checkOptionItem(optionItem, result);
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
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OptionItem optionItem = prepareTestOptionItem();

        Optional<OptionItem> resultOptional =
                dao.getOptionalGeneralEntity(OptionItem.class, parameter);

        assertTrue(resultOptional.isPresent());
        OptionItem result = resultOptional.get();

        assertEquals(optionItem.getId(), result.getId());
        assertEquals(optionItem.getOptionName(), result.getOptionName());

        assertEquals(optionItem.getOptionGroup().getId(), result.getOptionGroup().getId());
        assertEquals(optionItem.getOptionGroup().getName(), result.getOptionGroup().getName());
    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_Failure() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(OptionItem.class, parameter);
        });
    }

    @Test
    void getOptionalEntityWithDependencies_OptionEmpty() {
        Parameter parameter = new Parameter("id", 100L);

        Optional<OptionItem> result =
                dao.getOptionalGeneralEntity(OptionItem.class, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OptionItem optionItem = prepareTestOptionItem();

        OptionItem result = dao.getGeneralEntity(parameter);

        checkOptionItem(optionItem, result);
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
        loadDataSet("/datasets/option/option_item/testOptionItemDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        OptionItem optionItem = prepareTestOptionItem();

        OptionItem result = dao.getGeneralEntity(OptionItem.class, parameter);

        checkOptionItem(optionItem, result);
    }

    @Test
    void getEntityWithDependenciesWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(OptionItem.class, parameter);
        });
    }

}