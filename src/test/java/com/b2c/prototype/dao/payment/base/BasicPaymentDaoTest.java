package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.AbstractGeneralEntityDaoTest;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.util.CardUtil;
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

class BasicPaymentDaoTest extends AbstractGeneralEntityDaoTest {

    @BeforeAll
    public static void setup() {
        IThreadLocalSessionManager sessionManager = new ThreadLocalSessionManager(sessionFactory);
        entityIdentifierDao = new EntityIdentifierDao(sessionManager, getEntityMappingManager());
        dao = new BasicPaymentDao(sessionFactory, entityIdentifierDao);
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable cardEntityTable = new EntityTable(CreditCard.class, "credit_card");
        EntityTable currencyEntityTable = new EntityTable(Currency.class, "currency");
        EntityTable priceEntityTable = new EntityTable(Price.class, "price");
        EntityTable paymentEntityTable = new EntityTable(Payment.class, "payment");

        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(cardEntityTable);
        entityMappingManager.addEntityTable(currencyEntityTable);
        entityMappingManager.addEntityTable(priceEntityTable);
        entityMappingManager.addEntityTable(paymentEntityTable);
        return entityMappingManager;
    }

    private Payment prepareToSavePayment() {
        CreditCard creditCard = CreditCard.builder()
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv(818)
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .method("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        Price price = Price.builder()
                .amount(10)
                .currency(currency)
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .currency(currency)
                .build();
        return Payment.builder()
                .paymentId("1")
                .currencyDiscount(currencyDiscount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .fullPrice(price)
                .build();
    }

    private Payment prepareToUpdatePayment() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("Update name")
                .ownerSecondName("Update secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv(818)
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .method("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .currency(currency)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(20)
                .currency(currency)
                .build();

        return Payment.builder()
                .id(1L)
                .paymentId("Update 1")
                .currencyDiscount(currencyDiscount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .fullPrice(price)
                .build();
    }

    private Payment prepareTestPayment() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .dateOfExpire("06/28")
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive("06/28"))
                .cvv(818)
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .method("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .value("USD")
                .build();
        CurrencyDiscount currencyDiscount = CurrencyDiscount.builder()
                .id(1L)
                .amount(5)
                .currency(currency)
                .build();
        Price price = Price.builder()
                .id(1L)
                .amount(10)
                .currency(currency)
                .build();

        return Payment.builder()
                .id(1L)
                .paymentId("1")
                .currencyDiscount(currencyDiscount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .fullPrice(price)
                .build();
    }

    private void checkPayment(Payment expectedPayment, Payment actualPayment) {
        assertEquals(expectedPayment.getId(), actualPayment.getId());
        assertEquals(expectedPayment.getPaymentId(), actualPayment.getPaymentId());
        assertEquals(expectedPayment.getCreditCard().getId(), actualPayment.getCreditCard().getId());
        assertEquals(expectedPayment.getCreditCard().getCardNumber(), actualPayment.getCreditCard().getCardNumber());
        assertEquals(expectedPayment.getCreditCard().getDateOfExpire(), actualPayment.getCreditCard().getDateOfExpire());
        assertEquals(expectedPayment.getCreditCard().getOwnerName(), actualPayment.getCreditCard().getOwnerName());
        assertEquals(expectedPayment.getCreditCard().getOwnerSecondName(), actualPayment.getCreditCard().getOwnerSecondName());
        assertEquals(expectedPayment.getCreditCard().isActive(), actualPayment.getCreditCard().isActive());
        assertEquals(expectedPayment.getCreditCard().getCvv(), actualPayment.getCreditCard().getCvv());

//        assertEquals(expectedPayment.getPaymentMethod().getId(), actualPayment.getPaymentMethod().getId());
//        assertEquals(expectedPayment.getPaymentMethod().getMethod(), actualPayment.getPaymentMethod().getMethod());
//
//        assertEquals(expectedPayment.getFullPrice().getId(), actualPayment.getFullPrice().getId());
//        assertEquals(expectedPayment.getFullPrice().getAmount(), actualPayment.getFullPrice().getAmount());

//        Currency currency = dao.initializeEntity(Currency.class, actualPayment.getFullPrice().getCurrency().getId());
//        assertEquals(expectedPayment.getFullPrice().getCurrency().getId(), currency.getId());
//        assertEquals(expectedPayment.getFullPrice().getCurrency().getName(), currency.getName());
    }

    @Test
    void getEntityList_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Payment payment = prepareTestPayment();
        List<Payment> resultList =
                dao.getGeneralEntityList(parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkPayment(payment, result));
    }

    @Test
    void getEntityListWithClass_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Payment payment = prepareTestPayment();
        List<Payment> resultList =
                dao.getGeneralEntityList(Payment.class, parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkPayment(payment, result));
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
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Payment payment = prepareToSavePayment();
        payment.getCreditCard().setId(1L);
        payment.setPaymentMethod(PaymentMethod.builder()
                        .id(1L)
                        .method("Blik")
                        .build());
        payment.setCreditCard(null);
        payment.setCurrencyDiscount(null);

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, List.of(payment.getFullPrice()));
        generalEntity.addEntityPriority(2, payment);

        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/payment/payment/savePaymentWithoutDependentsDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Payment payment = prepareToSavePayment();
        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, List.of(payment.getFullPrice(), payment.getCreditCard()));
        generalEntity.addEntityPriority(2, payment);
        dao.saveGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/payment/payment/savePaymentDependentsDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        Payment payment = new Payment();
        payment.setId(1L);
        payment.setPaymentId("1");

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, payment);

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
        doThrow(new RuntimeException()).when(session).persist(payment);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Payment payment = prepareToSavePayment();
            s.persist(payment.getCreditCard());
            s.persist(payment.getFullPrice());
            s.persist(payment);
        };

        dao.saveGeneralEntity(consumer);
        verifyExpectedData("/datasets/payment/payment/savePaymentDependentsDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.saveGeneralEntity(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Supplier<Payment> paymentSupplier = this::prepareToUpdatePayment;
        dao.updateGeneralEntity(paymentSupplier);
        verifyExpectedData("/datasets/payment/payment/updatePaymentDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Payment payment = prepareToSavePayment();
        payment.setId(1L);
        payment.setPaymentId("Update 1");

        Supplier<Payment> paymentSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(paymentSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Payment paymentToUpdate = prepareToUpdatePayment();
            s.merge(paymentToUpdate.getFullPrice());
            s.merge(paymentToUpdate.getCreditCard());
            s.merge(paymentToUpdate);
        };
        dao.updateGeneralEntity(consumer);
        verifyExpectedData("/datasets/payment/payment/updatePaymentDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Consumer<Session> paymentSupplier = (Session s) -> {
            Payment payment = prepareToSavePayment();
            payment.setId(1L);
            payment.setPaymentId("Update 1");
            s.merge(payment);
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.updateGeneralEntity(paymentSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(parameter);
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithCardDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Payment payment = prepareTestPayment();
            s.remove(payment.getFullPrice());
            s.remove(payment);
        };

        dao.deleteGeneralEntity(consumer);
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithCardDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
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
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Payment payment = prepareTestPayment();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, payment.getCreditCard());
        generalEntity.addEntityPriority(1, payment.getFullPrice());
        generalEntity.addEntityPriority(2, payment);

        dao.deleteGeneralEntity(generalEntity);
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
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

        Payment payment = prepareTestPayment();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(2, payment);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(generalEntity);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.deleteGeneralEntity(Payment.class, parameter);
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithCardDataSet.yml");
    }

    @Test
    void deleteEntity_transactionFailure() {
        Payment payment = new Payment();
        payment.setPaymentId("1");

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
        generalEntity.addEntityPriority(2, payment);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntityWithClass_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Payment payment = new Payment();
        payment.setPaymentId("1");

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
        generalEntity.addEntityPriority(2, payment);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteGeneralEntity(Payment.class, parameter);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void getOptionalEntityWithDependencies_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Payment payment = prepareTestPayment();
        Optional<Payment> resultOptional =
                dao.getOptionalGeneralEntity(parameter);

        assertTrue(resultOptional.isPresent());
        Payment result = resultOptional.get();
        checkPayment(payment, result);

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
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Payment payment = prepareTestPayment();

        Optional<Payment> resultOptional =
                dao.getOptionalGeneralEntity(Payment.class, parameter);

        assertTrue(resultOptional.isPresent());
        Payment result = resultOptional.get();

        checkPayment(payment, result);
    }

    @Test
    void getOptionalEntityWithDependenciesWithClass_Failure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id1", 1L);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.getOptionalGeneralEntity(Payment.class, parameter);
        });
    }

    @Test
    void getOptionalEntityWithDependencies_OptionEmpty() {
        Parameter parameter = new Parameter("id", 100L);

        Optional<Payment> result =
                dao.getOptionalGeneralEntity(Payment.class, parameter);

        assertTrue(result.isEmpty());
    }

    @Test
    void getEntityWithDependencies_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Payment payment = prepareTestPayment();

        Payment result = dao.getGeneralEntity(parameter);

        checkPayment(payment, result);
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
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Payment payment = prepareTestPayment();

        Payment result = dao.getGeneralEntity(Payment.class, parameter);

        checkPayment(payment, result);
    }

    @Test
    void getEntityWithDependenciesWithClass_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getGeneralEntity(Payment.class, parameter);
        });
    }

}