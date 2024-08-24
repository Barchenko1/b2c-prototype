package com.b2c.prototype.service.payment.base;

import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.modal.dto.request.RequestCardDto;
import com.b2c.prototype.modal.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.dto.request.RequestPaymentDto;
import com.b2c.prototype.modal.dto.update.RequestPaymentDtoUpdate;
import com.b2c.prototype.modal.entity.payment.Card;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.entity.item.Discount;
import com.b2c.prototype.modal.constant.PaymentMethodEnum;
import com.b2c.prototype.dao.wrapper.IEntityStringMapWrapper;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.payment.IPaymentService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.Optional;

import static com.b2c.prototype.util.Query.DELETE_PAYMENT_BY_ORDER_ID;
import static com.b2c.prototype.util.Query.DELETE_PAYMENT_HAVE_NOT_ORDER;
import static com.b2c.prototype.util.Query.SELECT_CARD_BY_OWNER_USERNAME;
import static com.b2c.prototype.util.Query.SELECT_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY;
import static com.b2c.prototype.util.Query.SELECT_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS;
import static com.b2c.prototype.util.Query.SELECT_PAYMENT_BY_ORDER_ID;

@Slf4j
public class PaymentService implements IPaymentService {

    private final ThreadLocalSessionManager sessionManager;
    private final IAsyncProcessor asyncProcessor;
    private final IPaymentDao paymentDao;
    private final ICardDao cardDao;
    private final IDiscountDao discountDao;
    private final IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper;

    public PaymentService(ThreadLocalSessionManager sessionManager,
                          IAsyncProcessor asyncProcessor,
                          IPaymentDao paymentDao,
                          ICardDao cardDao,
                          IDiscountDao discountDao,
                          IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        this.sessionManager = sessionManager;
        this.asyncProcessor = asyncProcessor;
        this.paymentDao = paymentDao;
        this.cardDao = cardDao;
        this.discountDao = discountDao;
        this.paymentMethodEntityMapWrapper = paymentMethodEntityMapWrapper;
    }

    @Override
    public void savePayment(RequestPaymentDto requestPaymentDto) {
        Transaction transaction = null;
        try (Session session = sessionManager.getSession()){
            transaction = session.beginTransaction();
            Map<Class<?>, Object> processResultMap = executeAsyncProcess(requestPaymentDto);
            Card card = null;
            if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(requestPaymentDto.getPaymentMethod())) {
                card = getCard(requestPaymentDto.getCard());
                session.persist(card);
            }
            Payment payment = Payment.builder()
                    .amount(requestPaymentDto.getAmount())
                    .card(card)
                    .discount((Discount) processResultMap.get(Discount.class))
                    .paymentMethod((PaymentMethod) processResultMap.get(PaymentMethod.class))
                    .build();

            session.persist(payment);

            transaction.commit();
        } catch (Exception e) {
            log.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            sessionManager.closeSession();
        }
    }

    @Override
    public void updatePayment(RequestPaymentDtoUpdate requestPaymentDtoUpdate) {
        RequestPaymentDto requestPaymentDto = requestPaymentDtoUpdate.getNewEntityDto();
        String searchField = requestPaymentDtoUpdate.getSearchField();
        Transaction transaction = null;
        try (Session session = sessionManager.getSession()){
            transaction = session.beginTransaction();
            Map<Class<?>, Object> processResultMap = executeAsyncProcess(requestPaymentDtoUpdate);
            if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(requestPaymentDto.getPaymentMethod())) {
                Payment payment = (Payment) processResultMap.get(Payment.class);
                Optional.of(payment)
                        .ifPresent(p -> {
                            Card oldCard = p.getCard();
                            Card newCard = getCard(requestPaymentDto.getCard());
                            newCard.setId(oldCard.getId());
                            session.merge(newCard);
                        });
            }

            Optional<Payment> optionalPayment =
                    paymentDao.getOptionalEntityBySQLQueryWithParams(SELECT_PAYMENT_BY_ORDER_ID, searchField);

            if (optionalPayment.isPresent()) {
                Payment oldPayment = optionalPayment.get();
                Payment payment = Payment.builder()
                        .id(oldPayment.getId())
                        .amount(requestPaymentDto.getAmount())
//                        .card(card)
                        .discount((Discount) processResultMap.get(Discount.class))
                        .paymentMethod((PaymentMethod) processResultMap.get(PaymentMethod.class))
                        .build();

                session.merge(payment);
            }


            transaction.commit();
        } catch (Exception e) {
            log.warn("transaction error {}", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException(e);
        } finally {
            sessionManager.closeSession();
        }
    }

    @Override
    public void deletePaymentByOrderId(String orderId) {
        paymentDao.mutateEntityBySQLQueryWithParams(DELETE_PAYMENT_BY_ORDER_ID, orderId);
    }

    @Override
    public void deletePaymentHaveNotOrder() {
        paymentDao.mutateEntityBySQLQueryWithParams(DELETE_PAYMENT_HAVE_NOT_ORDER);
    }

    private Card getCard(RequestCardDto requestCardDto) {
        if (requestCardDto != null) {
            return Card.builder()
                    .cardNumber(requestCardDto.getCartNumber())
                    .dateOfExpire(requestCardDto.getDateOfExpire())
                    .cvv(requestCardDto.getCvv())
                    .isActive(CardUtil.isCardActive(requestCardDto.getDateOfExpire()))
                    .ownerName(requestCardDto.getOwnerName())
                    .ownerSecondName(requestCardDto.getOwnerSecondName())
                    .build();
        }
        return (Card) cardDao.getOptionalEntityBySQLQueryWithParams(
                        SELECT_CARD_BY_OWNER_USERNAME, "")
                .orElseThrow(RuntimeException::new);
    }

    private Discount getDiscount(RequestDiscountDto requestDiscountDto) {
        if (requestDiscountDto != null) {
            if (requestDiscountDto.isCurrency()) {
                return (Discount) discountDao.getOptionalEntityBySQLQueryWithParams(SELECT_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY,
                        requestDiscountDto.getAmount(), true
                ).orElse(null);
            }
            if (requestDiscountDto.isPercents()) {
                return (Discount) discountDao.getOptionalEntityBySQLQueryWithParams(SELECT_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS,
                        requestDiscountDto.getAmount(), false
                ).orElse(null);
            }
        }
        return null;
    }

    private Map<Class<?>, Object> executeAsyncProcess(RequestPaymentDto requestPaymentDto) {
        Task paymentMethodTask = new Task(
                () -> paymentMethodEntityMapWrapper.getEntity(requestPaymentDto.getPaymentMethod()),
                PaymentMethod.class
        );
        Task discountTask = new Task(
                () -> getDiscount(requestPaymentDto.getDiscount()),
                Discount.class
        );
        return asyncProcessor.process(paymentMethodTask, discountTask);
    }

    private Map<Class<?>, Object> executeAsyncProcess(RequestPaymentDtoUpdate requestPaymentDtoUpdate) {
        RequestPaymentDto requestPaymentDto = requestPaymentDtoUpdate.getNewEntityDto();
        String searchField = requestPaymentDtoUpdate.getSearchField();
        Task paymentMethodTask = new Task(
                () -> paymentMethodEntityMapWrapper.getEntity(requestPaymentDto.getPaymentMethod()),
                PaymentMethod.class
        );
        Task discountTask = new Task(
                () -> getDiscount(requestPaymentDto.getDiscount()),
                Discount.class
        );
        Task paymentTask = new Task(
                () -> paymentDao.getOptionalEntityBySQLQueryWithParams(SELECT_PAYMENT_BY_ORDER_ID, searchField),
                Payment.class
        );
        return asyncProcessor.process(paymentMethodTask, discountTask, paymentTask);
    }

}
