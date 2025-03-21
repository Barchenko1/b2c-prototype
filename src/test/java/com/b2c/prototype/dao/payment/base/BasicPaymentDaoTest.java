package com.b2c.prototype.dao.payment.base;

import com.b2c.prototype.dao.AbstractCustomEntityDaoTest;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.price.Currency;
import com.b2c.prototype.modal.entity.price.Price;
import com.b2c.prototype.util.CardUtil;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class BasicPaymentDaoTest extends AbstractCustomEntityDaoTest {

    @BeforeAll
    public static void setup() {
        queryService = new QueryService(getEntityMappingManager());
        dao = new BasicPaymentDao(sessionFactory, queryService);
    }

    private static IEntityMappingManager getEntityMappingManager() {
        EntityTable cardEntityTable = new EntityTable(CreditCard.class, "credit_card");
        EntityTable discount = new EntityTable(Discount.class, "discount");
        EntityTable currencyEntityTable = new EntityTable(Currency.class, "currency");
        EntityTable priceEntityTable = new EntityTable(Price.class, "price");
        EntityTable paymentEntityTable = new EntityTable(Payment.class, "payment");

        IEntityMappingManager entityMappingManager = new EntityMappingManager();
        entityMappingManager.addEntityTable(cardEntityTable);
        entityMappingManager.addEntityTable(currencyEntityTable);
        entityMappingManager.addEntityTable(priceEntityTable);
        entityMappingManager.addEntityTable(discount);
        entityMappingManager.addEntityTable(paymentEntityTable);
        return entityMappingManager;
    }

    private Payment prepareToSavePayment() {
        CreditCard creditCard = CreditCard.builder()
                .cardNumber("4444-1111-2222-3333")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value("Card")
                .label("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Price price = Price.builder()
                .amount(10)
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
        return Payment.builder()
                .paymentId("1")
                .discount(discount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .commissionPrice(price)
                .totalPrice(price)
                .build();
    }

    private Payment prepareToUpdatePayment() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("Update name")
                .ownerSecondName("Update secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value("Card")
                .label("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
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
                .discount(discount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .commissionPrice(price)
                .totalPrice(price)
                .build();
    }

    private Payment prepareTestPayment() {
        CreditCard creditCard = CreditCard.builder()
                .id(1L)
                .cardNumber("4444-1111-2222-3333")
                .monthOfExpire(6)
                .yearOfExpire(28)
                .ownerName("name")
                .ownerSecondName("secondName")
                .isActive(CardUtil.isCardActive(6, 28))
                .cvv("818")
                .build();
        PaymentMethod paymentMethod = PaymentMethod.builder()
                .id(1L)
                .value("Card")
                .label("Card")
                .build();
        Currency currency = Currency.builder()
                .id(1L)
                .label("USD")
                .value("USD")
                .build();
        Discount discount = Discount.builder()
                .id(1L)
                .amount(5)
                .charSequenceCode("abc")
                .isActive(true)
                .isPercent(false)
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
                .discount(discount)
                .paymentMethod(paymentMethod)
                .creditCard(creditCard)
                .commissionPrice(price)
                .totalPrice(price)
                .build();
    }

    private void checkPayment(Payment expectedPayment, Payment actualPayment) {
        assertEquals(expectedPayment.getId(), actualPayment.getId());
        assertEquals(expectedPayment.getPaymentMethod().getId(), actualPayment.getPaymentMethod().getId());
//        assertEquals(expectedPayment.getCreditCard().getId(), actualPayment.getCreditCard().getId());
//        assertEquals(expectedPayment.getCreditCard().getCardNumber(), actualPayment.getCreditCard().getCardNumber());
//        assertEquals(expectedPayment.getCreditCard().getDateOfExpire(), actualPayment.getCreditCard().getDateOfExpire());
//        assertEquals(expectedPayment.getCreditCard().getOwnerName(), actualPayment.getCreditCard().getOwnerName());
//        assertEquals(expectedPayment.getCreditCard().getOwnerSecondName(), actualPayment.getCreditCard().getOwnerSecondName());
//        assertEquals(expectedPayment.getCreditCard().isActive(), actualPayment.getCreditCard().isActive());
//        assertEquals(expectedPayment.getCreditCard().getCvv(), actualPayment.getCreditCard().getCvv());

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
        List<Payment> resultList = dao.getNamedQueryEntityList("", parameter);

        assertEquals(1, resultList.size());
        resultList.forEach(result -> checkPayment(payment, result));
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
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Payment payment = prepareToSavePayment();
        payment.getCreditCard().setId(1L);
        payment.setPaymentMethod(PaymentMethod.builder()
                        .id(1L)
                        .value("Blik")
                        .build());
        payment.setCreditCard(null);
        payment.setDiscount(null);

        dao.persistEntity(payment);
        verifyExpectedData("/datasets/payment/payment/savePaymentWithoutDependentsDataSet.yml");
    }

    @Test
    void saveEntityWithDependencies_success() {
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Payment payment = prepareToSavePayment();
        dao.persistEntity(payment);
        verifyExpectedData("/datasets/payment/payment/savePaymentDependentsDataSet.yml");
    }

    @Test
    void saveEntity_transactionFailure() {
        Payment payment = new Payment();
        payment.setId(1L);

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
        doThrow(new RuntimeException()).when(session).persist(payment);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.persistEntity(payment);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void saveEntityConsumer_success() {
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Payment payment = prepareToSavePayment();
            s.persist(payment.getCreditCard());
            s.persist(payment.getTotalPrice());
            s.persist(payment);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/payment/payment/savePaymentDependentsDataSet.yml");
    }

    @Test
    void saveEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithoutCardDataSet.yml");
    }

    @Test
    void updateEntity_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Supplier<Payment> paymentSupplier = this::prepareToUpdatePayment;
        dao.updateEntity(paymentSupplier);
        verifyExpectedData("/datasets/payment/payment/updatePaymentDataSet.yml");
    }

    @Test
    void updateEntity_transactionFailure() {
        Payment payment = prepareToSavePayment();
        payment.setId(1L);

        Supplier<Payment> paymentSupplier = () -> {
            throw new RuntimeException();
        };

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.updateEntity(paymentSupplier);
        });

        assertEquals(IllegalStateException.class, exception.getClass());
    }

    @Test
    void updateEntityConsumer_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Payment paymentToUpdate = prepareToUpdatePayment();
            s.merge(paymentToUpdate.getTotalPrice());
            s.merge(paymentToUpdate.getCreditCard());
            s.merge(paymentToUpdate);
        };
        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/payment/payment/updatePaymentDataSet.yml");
    }

    @Test
    void updateEntityConsumer_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Payment payment = prepareToSavePayment();
            payment.setId(1L);
            s.merge(payment);
            throw new RuntimeException();
        };

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            dao.executeConsumer(consumer);
        });

    }

    @Test
    void deleteEntity_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1);

        dao.findEntityAndDelete(parameter);
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithCardDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_success() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Consumer<Session> consumer = (Session s) -> {
            Payment payment = prepareTestPayment();
            s.remove(payment.getTotalPrice());
            s.remove(payment);
        };

        dao.executeConsumer(consumer);
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithCardDataSet.yml");
    }

    @Test
    void deleteEntityByConsumer_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
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
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Payment payment = prepareTestPayment();

        dao.deleteEntity(payment);
        verifyExpectedData("/datasets/payment/payment/emptyPaymentWithCardDataSet.yml");
    }

    @Test
    void deleteEntityByGeneralEntity_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
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

        Payment payment = prepareTestPayment();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            dao.deleteEntity(payment);
        });

        assertEquals(RuntimeException.class, exception.getClass());
    }

    @Test
    void deleteEntity_transactionFailure() {
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Payment payment = new Payment();

        Parameter parameter = new Parameter("id", 1L);

        SessionFactory sessionFactory = mock(SessionFactory.class);
        Session session = mock(Session.class);
        Transaction transaction = mock(Transaction.class);
        NativeQuery<Payment> nativeQuery = mock(NativeQuery.class);

        try {
            Field sessionManagerField = AbstractEntityDao.class.getDeclaredField("sessionFactory");
            sessionManagerField.setAccessible(true);
            sessionManagerField.set(dao, sessionFactory);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(sessionFactory.openSession()).thenReturn(session);
        when(session.beginTransaction()).thenReturn(transaction);
//        when(session.createNativeQuery(anyString(), eq(Payment.class))).thenReturn(nativeQuery);
        when(nativeQuery.getSingleResult()).thenReturn(payment);
        doThrow(new RuntimeException()).when(session).remove(payment);

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
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);
        Payment payment = prepareTestPayment();
        Optional<Payment> resultOptional =
               dao.getNamedQueryOptionalEntity("", parameter);

        assertTrue(resultOptional.isPresent());
        Payment result = resultOptional.get();
        checkPayment(payment, result);

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
        loadDataSet("/datasets/payment/payment/testPaymentDataSet.yml");
        Parameter parameter = new Parameter("id", 1L);

        Payment payment = prepareTestPayment();

        Payment result = dao.getNamedQueryEntity("", parameter);

        checkPayment(payment, result);
    }

    @Test
    void getEntityWithDependencies_Failure() {
        Parameter parameter = new Parameter("id1", 1L);

        assertThrows(RuntimeException.class, () -> {
            dao.getNamedQueryEntity("", parameter);
        });
    }

}