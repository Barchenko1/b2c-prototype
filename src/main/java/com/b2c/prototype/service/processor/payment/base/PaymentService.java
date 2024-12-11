package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import com.b2c.prototype.modal.dto.request.CurrencyDiscountDto;
import com.b2c.prototype.modal.dto.request.PaymentDto;
import com.b2c.prototype.modal.dto.update.PaymentDtoUpdate;
import com.b2c.prototype.modal.entity.item.CurrencyDiscount;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.payment.PaymentMethod;
import com.b2c.prototype.modal.constant.PaymentMethodEnum;
import com.b2c.prototype.processor.IAsyncProcessor;
import com.b2c.prototype.processor.Task;
import com.b2c.prototype.service.processor.payment.IPaymentService;
import com.b2c.prototype.util.CardUtil;
import com.tm.core.processor.finder.parameter.Parameter;
import org.hibernate.Session;

import java.util.Map;
import java.util.function.Consumer;

public class PaymentService implements IPaymentService {

    private final IAsyncProcessor asyncProcessor;
    private final IPaymentDao paymentDao;
    private final ICreditCardDao cardDao;
    private final ICurrencyDiscountDao discountDao;
    private final IEntityCachedMap entityCachedMap;

    public PaymentService(IAsyncProcessor asyncProcessor,
                          IPaymentDao paymentDao,
                          ICreditCardDao cardDao,
                          ICurrencyDiscountDao discountDao,
                          IEntityCachedMap entityCachedMap) {
        this.asyncProcessor = asyncProcessor;
        this.paymentDao = paymentDao;
        this.cardDao = cardDao;
        this.discountDao = discountDao;
        this.entityCachedMap = entityCachedMap;
    }

    @Override
    public void savePayment(PaymentDto paymentDto) {
        CreditCard creditCard = null;
        if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(paymentDto.getPaymentMethod())) {
            creditCard = getCard(paymentDto.getCard());
        }
        Map<Class<?>, Object> processResultMap = executeAsyncProcess(paymentDto);
        Payment payment = Payment.builder()
//                .amount(requestPaymentDto.getAmount())
                .creditCard(creditCard)
                .currencyDiscount((CurrencyDiscount) processResultMap.get(CurrencyDiscount.class))
                .paymentMethod((PaymentMethod) processResultMap.get(PaymentMethod.class))
                .build();

    }

    @Override
    public void updatePayment(PaymentDtoUpdate requestPaymentDtoUpdate) {
        PaymentDto paymentDto = requestPaymentDtoUpdate.getNewEntityDto();
        String searchField = requestPaymentDtoUpdate.getSearchField();

        CreditCard creditCard;
        if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(paymentDto.getPaymentMethod())) {
            creditCard = getCard(paymentDto.getCard());
        } else {
            creditCard = null;
        }
        Map<Class<?>, Object> processResultMap = executeAsyncProcess(paymentDto);
        Payment payment = Payment.builder()
//                .amount(requestPaymentDto.getAmount())
                .creditCard(creditCard)
                .currencyDiscount((CurrencyDiscount) processResultMap.get(CurrencyDiscount.class))
                .paymentMethod((PaymentMethod) processResultMap.get(PaymentMethod.class))
                .build();

        Consumer<Session> consumer = session -> {
            session.merge(creditCard);
            session.merge(payment);
        };

//        super.updateEntity(consumer);
    }

    @Override
    public void deletePaymentByOrderId(String orderId) {
//        Parameter parameter = parameterFactory.createStringParameter("id", orderId);
//        paymentDao.deleteGeneralEntity(this.getClass(), parameter);
    }

    @Override
    public void deletePaymentHaveNotOrder() {
//        paymentDao.deleteRelationshipEntity();
    }

    private CreditCard getCard(CreditCardDto creditCardDto) {
        if (creditCardDto != null) {
            return CreditCard.builder()
                    .cardNumber(creditCardDto.getCardNumber())
                    .dateOfExpire(creditCardDto.getDateOfExpire())
                    .cvv(creditCardDto.getCvv())
                    .isActive(CardUtil.isCardActive(creditCardDto.getDateOfExpire()))
                    .ownerName(creditCardDto.getOwnerName())
                    .ownerSecondName(creditCardDto.getOwnerSecondName())
                    .build();
        }
        Parameter parameter = new Parameter("username", "");
        return (CreditCard) cardDao.getOptionalEntity(parameter)
                .orElseThrow(RuntimeException::new);
    }

    private CurrencyDiscount getCurrencyDiscount(CurrencyDiscountDto currencyDiscountDto) {

        return null;
    }

    private Map<Class<?>, Object> executeAsyncProcess(PaymentDto paymentDto) {
        Task paymentMethodTask = new Task(
                () -> entityCachedMap.getEntity(PaymentMethod.class, "value", paymentDto.getPaymentMethod()),
                PaymentMethod.class
        );
        Task discountTask = new Task(
                () -> getCurrencyDiscount(null),
                CurrencyDiscount.class
        );
        return asyncProcessor.process(paymentMethodTask, discountTask);
    }

    private Map<Class<?>, Object> executeAsyncProcess(PaymentDtoUpdate requestPaymentDtoUpdate) {
        PaymentDto paymentDto = requestPaymentDtoUpdate.getNewEntityDto();
        String searchField = requestPaymentDtoUpdate.getSearchField();
        Task paymentMethodTask = new Task(
                () -> entityCachedMap.getEntity(PaymentMethod.class, "value", paymentDto.getPaymentMethod()),
                PaymentMethod.class
        );
        Task discountTask = new Task(
                () -> getCurrencyDiscount(null),
                CurrencyDiscount.class
        );
        Task paymentTask = new Task(
                () -> {
                    Parameter parameter = new Parameter("order_id", searchField);
                    return paymentDao.getOptionalEntity(parameter);
                },
                Payment.class
        );
        return asyncProcessor.process(paymentMethodTask, discountTask, paymentTask);
    }

}
