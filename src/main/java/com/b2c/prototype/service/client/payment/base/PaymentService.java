package com.b2c.prototype.service.client.payment.base;

import com.b2c.prototype.dao.payment.ICardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.IDiscountDao;
import com.b2c.prototype.dao.payment.IPaymentMethodDao;
import com.b2c.prototype.modal.client.dto.request.RequestCardDto;
import com.b2c.prototype.modal.client.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.client.dto.request.RequestPaymentDto;
import com.b2c.prototype.modal.client.dto.update.RequestPaymentDtoUpdate;
import com.b2c.prototype.modal.client.entity.payment.Card;
import com.b2c.prototype.modal.client.entity.payment.Payment;
import com.b2c.prototype.modal.client.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.client.entity.item.Discount;
import com.b2c.prototype.modal.constant.PaymentMethodEnum;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.payment.IPaymentService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.ThreadLocalSessionManager;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static com.b2c.prototype.util.Query.DELETE_PAYMENT_BY_ORDER_ID;
import static com.b2c.prototype.util.Query.DELETE_PAYMENT_HAVE_NOT_ORDER;
import static com.b2c.prototype.util.Query.SELECT_CARD_BY_CARD_NUMBER;
import static com.b2c.prototype.util.Query.SELECT_CARD_BY_OWNER_USERNAME;
import static com.b2c.prototype.util.Query.SELECT_DISCOUNT_BY_AMOUNT_AND_IS_CURRENCY;
import static com.b2c.prototype.util.Query.SELECT_DISCOUNT_BY_AMOUNT_AND_IS_PERCENTS;
import static com.b2c.prototype.util.Query.SELECT_PAYMENT_BY_PAYMENT_ID;

@Slf4j
public class PaymentService implements IPaymentService {

    private final ThreadLocalSessionManager sessionManager;
    private final IPaymentDao paymentDao;
    private final ICardDao cardDao;
    private final IDiscountDao discountDao;
    private final IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper;

    public PaymentService(ThreadLocalSessionManager sessionManager,
                          IPaymentDao paymentDao,
                          ICardDao cardDao,
                          IDiscountDao discountDao,
                          IEntityStringMapWrapper<PaymentMethod> paymentMethodEntityMapWrapper) {
        this.sessionManager = sessionManager;
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

            PaymentMethod paymentMethod = paymentMethodEntityMapWrapper.getEntity(requestPaymentDto.getPaymentMethod());
            Discount discount = getDiscount(requestPaymentDto.getDiscount());
            Card card = null;
            if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(paymentMethod.getMethod())) {
                card = getCard(requestPaymentDto.getCard());
                session.persist(card);
            }
            Payment payment = Payment.builder()
                    .amount(requestPaymentDto.getAmount())
                    .card(card)
                    .discount(discount)
                    .paymentMethod(paymentMethod)
                    .paymentId(UUID.randomUUID().toString())
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

            PaymentMethod paymentMethod = paymentMethodEntityMapWrapper.getEntity(requestPaymentDto.getPaymentMethod());
            Discount discount = getDiscount(requestPaymentDto.getDiscount());
            Card card = null;
            if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(paymentMethod.getMethod())) {
                Optional<Card> optionalCard =
                        cardDao.getOptionalEntityBySQLQueryWithParams(SELECT_CARD_BY_CARD_NUMBER, searchField);
                card = optionalCard.orElseGet(() -> getCard(requestPaymentDto.getCard()));
                session.merge(card);
            }

            Optional<Payment> optionalPayment =
                    paymentDao.getOptionalEntityBySQLQueryWithParams(SELECT_PAYMENT_BY_PAYMENT_ID, searchField);

            if (optionalPayment.isPresent()) {
                Payment oldPayment = optionalPayment.get();
                Payment payment = Payment.builder()
                        .id(oldPayment.getId())
                        .amount(requestPaymentDto.getAmount())
                        .card(card)
                        .discount(discount)
                        .paymentMethod(paymentMethod)
                        .paymentId(searchField)
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

}
