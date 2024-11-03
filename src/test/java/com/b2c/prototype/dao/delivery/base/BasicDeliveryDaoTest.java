package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
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

class BasicDeliveryDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, getEntityMappingManager());
        dao = new BasicDeliveryDao(sessionFactory, entityIdentifierDao);
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable addressEntityTable = new EntityTable(Address.class, "address");
        EntityTable deliveryTypeEntityTable = new EntityTable(DeliveryType.class, "delivery_type");
        EntityTable deliveryEntityTable = new EntityTable(Delivery.class, "delivery");

        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(addressEntityTable);
        entityMappingManager.addEntityTable(deliveryTypeEntityTable);
        entityMappingManager.addEntityTable(deliveryEntityTable);
        return entityMappingManager;
    }

    private Delivery prepareToSaveDelivery() {
        Country country = Country.builder()
                .id(1L)
                .name("USA")
                .build();
        Address address = Address.builder()
                .country(country)
                .street("street")
                .buildingNumber(1)
                .apartmentNumber(101)
                .flor(9)
                .zipCode("90000")
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .name("Post")
                .build();


        return Delivery.builder()
                .address(address)
                .deliveryType(deliveryType)
                .build();
    }

    private Delivery prepareToUpdateDelivery() {
        Country country = Country.builder()
                .id(1L)
                .name("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("Update street")
                .buildingNumber(1)
                .apartmentNumber(101)
                .flor(9)
                .zipCode("90001")
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .name("Post")
                .build();

        return Delivery.builder()
                .id(1L)
                .address(address)
                .deliveryType(deliveryType)
                .build();
    }

    private Delivery prepareTestDelivery() {
        Country country = Country.builder()
                .id(1L)
                .name("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .buildingNumber(1)
                .apartmentNumber(101)
                .flor(9)
                .zipCode("90000")
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .name("Post")
                .build();

        return Delivery.builder()
                .id(1L)
                .address(address)
                .deliveryType(deliveryType)
                .build();
    }

    private void checkDelivery(Delivery expectedDelivery, Delivery actualDelivery) {
        assertEquals(expectedDelivery.getId(), actualDelivery.getId());

        Address address = dao.initializeEntity(Address.class, actualDelivery.getAddress().getId());
        assertEquals(expectedDelivery.getAddress().getId(), address.getId());
        assertEquals(expectedDelivery.getAddress().getStreet(), address.getStreet());
        assertEquals(expectedDelivery.getAddress().getFlor(), address.getFlor());
        assertEquals(expectedDelivery.getAddress().getBuildingNumber(), address.getBuildingNumber());
        assertEquals(expectedDelivery.getAddress().getApartmentNumber(), address.getApartmentNumber());
        assertEquals(expectedDelivery.getAddress().getZipCode(), address.getZipCode());

        DeliveryType deliveryType = dao.initializeEntity(DeliveryType.class, actualDelivery.getDeliveryType().getId());
        assertEquals(expectedDelivery.getDeliveryType().getId(), deliveryType.getId());
        assertEquals(expectedDelivery.getDeliveryType().getName(), deliveryType.getName());

        Country country = dao.initializeEntity(Country.class, address.getCountry().getId());
        assertEquals(expectedDelivery.getAddress().getCountry().getId(), country.getId());
        assertEquals(expectedDelivery.getAddress().getCountry().getName(), country.getName());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Delivery delivery = prepareTestDelivery();
        List<Delivery> resultList =
                dao.getGeneralEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkDelivery(delivery, result);
        });
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Delivery delivery = prepareTestDelivery();
        List<Delivery> resultList =
                dao.getGeneralEntityList(Delivery.class, parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkDelivery(delivery, result);
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
        loadDataSet("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
        Delivery delivery = prepareToSaveDelivery();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, delivery.getAddress());
        generalEntity.addEntityPriority(2, delivery);

        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/delivery/delivery/saveDeliveryWithAddressDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
        Delivery delivery = prepareToSaveDelivery();
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, delivery.getAddress());
        generalEntity.addEntityPriority(2, delivery);
        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/delivery/delivery/saveDeliveryWithAddressDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        Delivery delivery = new Delivery();
        delivery.setId(1L);

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, delivery);

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
        doThrow(new RuntimeException()).when(session).persist(delivery);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Delivery delivery = prepareToSaveDelivery();
            s.persist(delivery.getAddress());
            s.persist(delivery);
        };

        dao.saveGeneralEntity(consumer);
        verifyExpectedData("/datasets/delivery/delivery/saveDeliveryWithAddressDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Supplier<Delivery> supplier = this::prepareToUpdateDelivery;
        dao.updateGeneralEntity(supplier);
        verifyExpectedData("/datasets/delivery/delivery/updateDeliveryDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<Delivery> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(supplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Delivery deliveryToUpdate = prepareToUpdateDelivery();
            s.merge(deliveryToUpdate);
        };
        dao.updateGeneralEntity(consumer);
        verifyExpectedData("/datasets/delivery/delivery/updateDeliveryDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Consumer<Session> RootTestEntitySupplier = (Session s) -> {
            Delivery delivery = prepareToUpdateDelivery();
            s.merge(delivery);
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(RootTestEntitySupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(parameter);
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithAddressDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Delivery delivery = prepareTestDelivery();
            s.remove(delivery.getAddress());
            s.remove(delivery);
        };

        dao.deleteGeneralEntity(consumer);
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
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
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Delivery delivery = prepareTestDelivery();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, delivery.getAddress());
        generalEntity.addEntityPriority(2, delivery);

        dao.deleteGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
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

        Delivery delivery = prepareTestDelivery();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, delivery);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(Delivery.class, parameter);
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithAddressDataSet.yml");
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Delivery delivery = new Delivery();

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
        generalEntity.addEntityPriority(2, delivery);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Delivery delivery = new Delivery();

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
        generalEntity.addEntityPriority(2, delivery);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(Delivery.class, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Delivery delivery = prepareTestDelivery();
        Optional<Delivery> resultOptional = dao.getOptionalGeneralEntity(parameter);

        assertTrue(resultOptional.isPresent());
        Delivery result = resultOptional.get();

        checkDelivery(delivery, result);
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
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Delivery delivery = prepareTestDelivery();

        Optional<Delivery> resultOptional =
                dao.getOptionalGeneralEntity(Delivery.class, parameter);

        assertTrue(resultOptional.isPresent());
        Delivery result = resultOptional.get();

        checkDelivery(delivery, result);
    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_Failure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(Delivery.class, parameter);
        });
    }

    @Test
    void getOptionalEntityWithDependencies_OptionEmpty() {
        Parameter parameter = new Parameter("id", 100L);

        Optional<Delivery> result =
                dao.getOptionalGeneralEntity(Delivery.class, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Delivery delivery = prepareTestDelivery();
        Delivery result = dao.getGeneralEntity(parameter);

        checkDelivery(delivery, result);
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
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Delivery delivery = prepareTestDelivery();

        Delivery result = dao.getGeneralEntity(Delivery.class, parameter);

        checkDelivery(delivery, result);
    }

    @Test
    void getEntityWithDependenciesWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(Delivery.class, parameter);
        });
    }

}