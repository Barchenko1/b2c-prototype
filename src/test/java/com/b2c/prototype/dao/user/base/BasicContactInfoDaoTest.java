package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasicContactInfoDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ContactInfo.class, "contact_info"));
        queryService = new QueryService(entityMappingManager);
        dao = new BasicContactInfoDao(sessionFactory, queryService);
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

    private ContactInfo prepareTestContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return ContactInfo.builder()
                .id(1L)
                .firstName("Wolter")
                .lastName("White")
                .contactPhone(contactPhone)
                .build();
    }

    private ContactInfo prepareToSaveContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .value("+11")
                .label("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return ContactInfo.builder()
                .firstName("Wolter")
                .lastName("White")
                .contactPhone(contactPhone)
                .build();
    }

    private ContactInfo prepareToUpdateContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(2L)
                .value("+22")
                .label("+22")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("222-222-222")
                .build();
        return ContactInfo.builder()
                .id(1L)
                .firstName("Update Wolter")
                .lastName("Update White")
                .contactPhone(contactPhone)
                .build();
    }

    private void checkBeneficiary(ContactInfo expectedContactInfo, ContactInfo actualContactInfo) {

    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ContactInfo contactInfo = prepareTestContactInfo();
        List<ContactInfo> resultList =
                dao.getNamedQueryEntityList("", parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkBeneficiary(contactInfo, result));
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
        loadDataSet("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
        ContactInfo contactInfo = prepareToSaveContactInfo();

        dao.persistEntity(contactInfo);
        verifyExpectedData("/datasets/user/contact_info/saveContactInfoDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
        ContactInfo contactInfo = prepareToSaveContactInfo();
        dao.persistEntity(contactInfo);
        verifyExpectedData("/datasets/user/contact_info/saveContactInfoDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(1L);

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
        doThrow(new RuntimeException()).when(session).persist(contactInfo);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(contactInfo);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ContactInfo contactInfo = prepareToSaveContactInfo();
            s.persist(contactInfo.getContactPhone());
            s.persist(contactInfo);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/contact_info/saveContactInfoDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Supplier<ContactInfo> supplier = this::prepareToUpdateContactInfo;
        dao.updateEntity(supplier);
        verifyExpectedData("/datasets/user/contact_info/updateContactInfoDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        ContactInfo contactInfo = prepareToSaveContactInfo();
        contactInfo.setId(1L);

        Supplier<ContactInfo> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ContactInfo contactInfo = prepareToUpdateContactInfo();
            s.merge(contactInfo.getContactPhone());
            s.merge(contactInfo);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/contact_info/updateContactInfoDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ContactInfo contactInfo = prepareToUpdateContactInfo();
            s.merge(contactInfo);
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void findEntityAndDelete_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            ContactInfo contactInfo = prepareTestContactInfo();
            s.remove(contactInfo);
            s.remove(contactInfo.getContactPhone());
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
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
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        ContactInfo contactInfo = prepareTestContactInfo();

        dao.deleteEntity(contactInfo);
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
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

        ContactInfo contactInfo = prepareTestContactInfo();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(contactInfo);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        ContactInfo contactInfo = prepareTestContactInfo();
        Parameter parameter = new Parameter("id", 1L);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<ContactInfo> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
//        when(session.createNativeQuery(anyString(), eq(ContactInfo.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(contactInfo);
        doThrow(new RuntimeException()).when(session).remove(contactInfo);

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
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ContactInfo contactInfo = prepareTestContactInfo();
        Optional<ContactInfo> resultOptional =
               dao.getNamedQueryOptionalEntity("", parameter);

        assertTrue(resultOptional.isPresent());
        ContactInfo result = resultOptional.get();
        checkBeneficiary(contactInfo, result);

    }

    @Test
    void getOptionalEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
           dao.getNamedQueryOptionalEntity("", parameter);
        });

    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        ContactInfo contactInfo = prepareTestContactInfo();

        ContactInfo result = dao.getNamedQueryEntity("", parameter);

        checkBeneficiary(contactInfo, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

}