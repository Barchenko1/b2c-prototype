package com.b2c.prototype.dao.user.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.user.ContactInfo;
import com.b2c.prototype.modal.entity.user.ContactPhone;
import com.b2c.prototype.modal.entity.user.CountryPhoneCode;
import com.tm.core.dao.general.AbstractGeneralEntityDao;
import com.tm.core.dao.identifier.EntityIdentifierDao;
import com.tm.core.modal.GeneralEntity;
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

class BasicContactInfoDaoTest extends AbstractGeneralEntityDaoTest {
    
    @BeforeAll
    public static void setup() {
        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(new EntityTable(ContactInfo.class, "contact_info"));
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, entityMappingManager);
        dao = new BasicContactInfoDao(sessionFactory, entityIdentifierDao);
    }

    @BeforeEach
    public void cleanUpMiddleTable() {
        try (Connection connection = connectionHolder.getConnection()) {
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();

            statement.execute("TRUNCATE TABLE contact_phone RESTART IDENTITY CASCADE");

            connection.commit();
        } catch (Exception e) {
            throw new RuntimeException("Failed to clean table: item_option", e);
        }
    }
    
    private ContactInfo prepareTestContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return ContactInfo.builder()
                .id(1L)
                .name("Wolter")
                .secondName("White")
                .contactPhone(contactPhone)
                .build();
    }
    
    private ContactInfo prepareToSaveContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(1L)
                .code("+11")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("111-111-111")
                .build();
        return ContactInfo.builder()
                .name("Wolter")
                .secondName("White")
                .contactPhone(contactPhone)
                .build();
    }
    
    private ContactInfo prepareToUpdateContactInfo() {
        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
                .id(2L)
                .code("+22")
                .build();
        ContactPhone contactPhone = ContactPhone.builder()
                .id(1L)
                .countryPhoneCode(countryPhoneCode)
                .phoneNumber("222-222-222")
                .build();
        return ContactInfo.builder()
                .id(1L)
                .name("Update Wolter")
                .secondName("Update White")
                .contactPhone(contactPhone)
                .build();
    }
    
    private void checkContactInfo(ContactInfo expectedContactInfo, ContactInfo actualContactInfo) {
        
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ContactInfo contactInfo = prepareTestContactInfo();
        List<ContactInfo> resultList =
                dao.getGeneralEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkContactInfo(contactInfo, result));
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ContactInfo contactInfo = prepareTestContactInfo();
        List<ContactInfo> resultList =
                dao.getGeneralEntityList(ContactInfo.class, parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkContactInfo(contactInfo, result));
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
        loadDataSet("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
        ContactInfo contactInfo = prepareToSaveContactInfo();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, List.of(contactInfo.getContactPhone()));
        generalEntity.addEntityPriority(2, contactInfo);

        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/user/contact_info/saveContactInfoDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
        ContactInfo contactInfo = prepareToSaveContactInfo();
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, List.of(contactInfo.getContactPhone()));
        generalEntity.addEntityPriority(2, contactInfo);
        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/user/contact_info/saveContactInfoDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        ContactInfo contactInfo = new ContactInfo();
        contactInfo.setId(1L);

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, contactInfo);

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
        doThrow(new RuntimeException()).when(session).persist(contactInfo);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(generalEntity);
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

        dao.saveGeneralEntity(consumer);
        verifyExpectedData("/datasets/user/contact_info/saveContactInfoDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Supplier<ContactInfo> supplier = this::prepareToUpdateContactInfo;
        dao.updateGeneralEntity(supplier);
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
            dao.updateGeneralEntity(supplier);
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
        dao.updateGeneralEntity(consumer);
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
            dao.updateGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(parameter);
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

        dao.deleteGeneralEntity(consumer);
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
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
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        ContactInfo contactInfo = prepareTestContactInfo();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, contactInfo.getContactPhone());
        generalEntity.addEntityPriority(2, contactInfo);

        dao.deleteGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
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

        ContactInfo contactInfo = prepareTestContactInfo();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, contactInfo);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(ContactInfo.class, parameter);
        verifyExpectedData("/datasets/user/contact_info/emptyContactInfoDataSet.yml");
    }

    @Test
    void deleteEntity_transactionFailure() {
        ContactInfo contactInfo = new ContactInfo();

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
        generalEntity.addEntityPriority(2, contactInfo);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_transactionFailure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        ContactInfo contactInfo = new ContactInfo();

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
        generalEntity.addEntityPriority(2, contactInfo);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(ContactInfo.class, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        ContactInfo contactInfo = prepareTestContactInfo();
        Optional<ContactInfo> resultOptional =
                dao.getOptionalGeneralEntity(parameter);

        assertTrue(resultOptional.isPresent());
        ContactInfo result = resultOptional.get();
        checkContactInfo(contactInfo, result);

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
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        ContactInfo contactInfo = prepareTestContactInfo();

        Optional<ContactInfo> resultOptional =
                dao.getOptionalGeneralEntity(ContactInfo.class, parameter);

        assertTrue(resultOptional.isPresent());
        ContactInfo result = resultOptional.get();

        checkContactInfo(contactInfo, result);
    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_Failure() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(ContactInfo.class, parameter);
        });
    }

    @Test
    void getOptionalEntityWithDependencies_OptionEmpty() {
        Parameter parameter = new Parameter("id", 100L);

        Optional<ContactInfo> result =
                dao.getOptionalGeneralEntity(ContactInfo.class, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        ContactInfo contactInfo = prepareTestContactInfo();

        ContactInfo result = dao.getGeneralEntity(parameter);

        checkContactInfo(contactInfo, result);
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
        loadDataSet("/datasets/user/contact_info/testContactInfoDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        ContactInfo contactInfo = prepareTestContactInfo();

        ContactInfo result = dao.getGeneralEntity(ContactInfo.class, parameter);

        checkContactInfo(contactInfo, result);
    }

    @Test
    void getEntityWithDependenciesWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(ContactInfo.class, parameter);
        });
    }
//
//    @Override
//    protected String getEmptyDataSetPath() {
//        return "/datasets/user/contact_info/emptyContactPhoneDataSet.yml";
//    }
//
//    @Override
//    protected EntityDataSet<?> getTestDataSet() {
//        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
//                .id(1L)
//                .code("+11")
//                .build();
//        ContactPhone contactPhone = ContactPhone.builder()
//                .phoneNumber("111-111-111")
//                .countryPhoneCode(countryPhoneCode)
//                .build();
//        ContactInfo contactInfo = ContactInfo.builder()
//                .id(1L)
//                .name("Wolter")
//                .secondName("White")
//                .contactPhone(contactPhone)
//                .build();
//        return new EntityDataSet<>(contactInfo, "/datasets/user/contact_info/testContactPhoneDataSet.yml");
//    }
//
//    @Override
//    protected EntityDataSet<?> getSaveDataSet() {
//        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
//                .id(1L)
//                .code("+11")
//                .build();
//        ContactPhone contactPhone = ContactPhone.builder()
//                .phoneNumber("111-111-111")
//                .countryPhoneCode(countryPhoneCode)
//                .build();
//        ContactInfo contactInfo = ContactInfo.builder()
//                .id(1L)
//                .name("Wolter")
//                .secondName("White")
//                .contactPhone(contactPhone)
//                .build();
//        return new EntityDataSet<>(contactInfo, "/datasets/user/contact_info/saveContactPhoneDataSet.yml");
//    }
//
//    @Override
//    protected EntityDataSet<?> getUpdateDataSet() {
//        CountryPhoneCode countryPhoneCode = CountryPhoneCode.builder()
//                .id(1L)
//                .code("+11")
//                .build();
//        ContactPhone contactPhone = ContactPhone.builder()
//                .phoneNumber("111-111-111")
//                .countryPhoneCode(countryPhoneCode)
//                .build();
//        ContactInfo contactInfo = ContactInfo.builder()
//                .id(1L)
//                .name("Wolter")
//                .secondName("White")
//                .contactPhone(contactPhone)
//                .build();
//        return new EntityDataSet<>(contactInfo, "/datasets/user/contact_info/updateContactPhoneDataSet.yml");
//    }
}