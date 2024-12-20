package com.b2c.prototype.dao.delivery.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.address.Address;
import com.b2c.prototype.modal.entity.address.Country;
import com.b2c.prototype.modal.entity.delivery.Delivery;
import com.b2c.prototype.modal.entity.delivery.DeliveryType;
import com.tm.core.dao.common.AbstractEntityDao;
import com.tm.core.dao.identifier.EntityIdentifierDao;
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
                .value("USA")
                .build();
        Address address = Address.builder()
                .country(country)
                .street("street")
                .street2("street2")
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Type")
                .build();


        return Delivery.builder()
                .address(address)
                .deliveryType(deliveryType)
                .build();
    }

    private Delivery prepareToUpdateDelivery() {
        Country country = Country.builder()
                .id(1L)
                .value("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("Update street")
                .street2("street2")
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90001")
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Type")
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
                .value("USA")
                .build();
        Address address = Address.builder()
                .id(1L)
                .country(country)
                .street("street")
                .street2("street2")
                .buildingNumber(1)
                .apartmentNumber(101)
                .florNumber(9)
                .zipCode("90000")
                .build();
        DeliveryType deliveryType = DeliveryType.builder()
                .id(1L)
                .value("Type")
                .build();

        return Delivery.builder()
                .id(1L)
                .address(address)
                .deliveryType(deliveryType)
                .build();
    }

    private void checkDelivery(Delivery expectedDelivery, Delivery actualDelivery) {
        assertEquals(expectedDelivery.getId(), actualDelivery.getId());

//        Address address = dao.initializeEntity(Address.class, actualDelivery.getAddress().getId());
//        assertEquals(expectedDelivery.getAddress().getId(), address.getId());
//        assertEquals(expectedDelivery.getAddress().getStreet(), address.getStreet());
//        assertEquals(expectedDelivery.getAddress().getFlorNumber(), address.getFlorNumber());
//        assertEquals(expectedDelivery.getAddress().getBuildingNumber(), address.getBuildingNumber());
//        assertEquals(expectedDelivery.getAddress().getApartmentNumber(), address.getApartmentNumber());
//        assertEquals(expectedDelivery.getAddress().getZipCode(), address.getZipCode());
//
//        DeliveryType deliveryType = dao.initializeEntity(DeliveryType.class, actualDelivery.getDeliveryType().getId());
//        assertEquals(expectedDelivery.getDeliveryType().getId(), deliveryType.getId());
//        assertEquals(expectedDelivery.getDeliveryType().getValue(), deliveryType.getValue());
//
//        Country country = dao.initializeEntity(Country.class, address.getCountry().getId());
//        assertEquals(expectedDelivery.getAddress().getCountry().getId(), country.getId());
//        assertEquals(expectedDelivery.getAddress().getCountry().getValue(), country.getValue());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Delivery delivery = prepareTestDelivery();
        List<Delivery> resultList =
                dao.getEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> {
            checkDelivery(delivery, result);
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
    void saveEntity_success() {
        loadDataSet("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
        Delivery delivery = prepareToSaveDelivery();

        dao.persistEntity(delivery);
        verifyExpectedData("/datasets/delivery/delivery/saveDeliveryWithAddressDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
        Delivery delivery = prepareToSaveDelivery();
        dao.persistEntity(delivery);
        verifyExpectedData("/datasets/delivery/delivery/saveDeliveryWithAddressDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        Delivery delivery = new Delivery();
        delivery.setId(1L);

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
        doThrow(new RuntimeException()).when(session).persist(delivery);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(delivery);
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

        dao.saveEntity(consumer);
        verifyExpectedData("/datasets/delivery/delivery/saveDeliveryWithAddressDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Supplier<Delivery> supplier = this::prepareToUpdateDelivery;
        dao.updateEntity(supplier);
        verifyExpectedData("/datasets/delivery/delivery/updateDeliveryDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Supplier<Delivery> supplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(supplier);
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
        dao.updateEntity(consumer);
        verifyExpectedData("/datasets/delivery/delivery/updateDeliveryDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Consumer<Session> rootTestEntitySupplier = (Session s) -> {
            Delivery delivery = prepareToUpdateDelivery();
            s.merge(delivery);
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(rootTestEntitySupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void findEntityAndDelete_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Delivery delivery = prepareTestDelivery();
            s.remove(delivery.getAddress());
            s.remove(delivery);
        };

        dao.deleteEntity(consumer);
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Delivery delivery = prepareTestDelivery();

        dao.deleteEntity(delivery);
        verifyExpectedData("/datasets/delivery/delivery/emptyDeliveryWithoutAddressDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
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

        Delivery delivery = prepareTestDelivery();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(delivery);
        });

        assertEquals(RuntimeException.class, exception.getClass());
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
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionManager");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionManager);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionManager.getSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
        doThrow(new RuntimeException()).when(session).remove(any(Object.class));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.findEntityAndDelete(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Delivery delivery = prepareTestDelivery();
        Optional<Delivery> resultOptional = dao.getOptionalEntity(parameter);

        assertTrue(resultOptional.isPresent());
        Delivery result = resultOptional.get();

        checkDelivery(delivery, result);
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
        loadDataSet("/datasets/delivery/delivery/testDeliveryDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Delivery delivery = prepareTestDelivery();
        Delivery result = dao.getEntity(parameter);

        checkDelivery(delivery, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getEntity(parameter);
        });
    }

}