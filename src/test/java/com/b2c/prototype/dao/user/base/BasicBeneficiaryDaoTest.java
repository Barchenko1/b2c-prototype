package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.dao.item.base.BasicBeneficiaryDao;
import com.b2c.prototype.modal.entity.order.Beneficiary;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
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

class BasicBeneficiaryDaoTest extends AbstractGeneralEntityDaoTest {
    
    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(Beneficiary.class, "beneficiary"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicBeneficiaryDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.execute("DELETE FROM beneficiary");
            statement.execute("DELETE FROM contact_info");
            statement.execute("DELETE FROM contact_phone");
            statement.execute("TRUNCATE TABLE beneficiary RESTART IDENTITY CASCADE");
            statement.execute("TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE");
            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }
    
    private Beneficiary prepareTestBeneficiary() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return Beneficiary.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .orderNumber(0)
                .contactPhone(contactPhone)
                .build();
    }
    
    private Beneficiary prepareToSaveBeneficiary() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return Beneficiary.builder()
                .firstName("Wolter")
                .lastName("White")
                .orderNumber(0)
                .contactPhone(contactPhone)
                .build();
    }
    
    private Beneficiary prepareToUpdateBeneficiary() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(2L)
                .code("+22")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("222-222-222")
                .build();
        return Beneficiary.builder()
                .id(1L)
                .firstName("Update Wolter")
                .lastName("Update White")
                .orderNumber(0)
                .contactPhone(contactPhone)
                .build();
    }
    
    private void checkBeneficiary(Beneficiary expectedBeneficiary, Beneficiary actualBeneficiary) {
        
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Beneficiary beneficiary = prepareTestBeneficiary();
        List<Beneficiary> resultList =
                dao.getEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkBeneficiary(beneficiary, result));
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
        loadDataSet("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
        Beneficiary beneficiary = prepareToSaveBeneficiary();

        dao.persistEntity(beneficiary);
        verifyExpectedData("/datasets/order/beneficiary/saveBeneficiaryDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
        Beneficiary beneficiary = prepareToSaveBeneficiary();
        dao.persistEntity(beneficiary);
        verifyExpectedData("/datasets/order/beneficiary/saveBeneficiaryDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setId(1L);

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
        doThrow(new RuntimeException()).when(session).persist(beneficiary);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(beneficiary);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Beneficiary beneficiary = prepareToSaveBeneficiary();
            s.persist(beneficiary.getContactPhone());
            s.persist(beneficiary);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/beneficiary/saveBeneficiaryDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Supplier<Beneficiary> supplier = this::prepareToUpdateBeneficiary;
        dao.updateEntity(supplier);
        verifyExpectedData("/datasets/order/beneficiary/updateBeneficiaryDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Beneficiary beneficiary = prepareToSaveBeneficiary();
        beneficiary.setId(1L);

        Supplier<Beneficiary> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Beneficiary beneficiary = prepareToUpdateBeneficiary();
            s.merge(beneficiary.getContactPhone());
            s.merge(beneficiary);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/beneficiary/updateBeneficiaryDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Beneficiary beneficiary = prepareToUpdateBeneficiary();
            s.merge(beneficiary);
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void findEntityAndDelete_success() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Beneficiary beneficiary = prepareTestBeneficiary();
            s.remove(beneficiary);
            s.remove(beneficiary.getContactPhone());
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Beneficiary beneficiary = prepareTestBeneficiary();

        dao.deleteEntity(beneficiary);
        verifyExpectedData("/datasets/order/beneficiary/emptyBeneficiaryDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
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

        Beneficiary beneficiary = prepareTestBeneficiary();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(beneficiary);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Beneficiary beneficiary = new Beneficiary();

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
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Beneficiary beneficiary = prepareTestBeneficiary();
        Optional<Beneficiary> resultOptional =
                dao.getOptionalEntity(parameter);

        assertTrue(resultOptional.isPresent());
        Beneficiary result = resultOptional.get();
        checkBeneficiary(beneficiary, result);

    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalEntity(parameter);
        });

    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/order/beneficiary/testBeneficiaryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Beneficiary beneficiary = prepareTestBeneficiary();

        Beneficiary result = dao.getEntity(parameter);

        checkBeneficiary(beneficiary, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });
    }
}