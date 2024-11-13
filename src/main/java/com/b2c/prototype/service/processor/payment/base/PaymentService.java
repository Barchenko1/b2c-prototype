package com.b2c.prototype.service.processor.payment.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.dao.payment.IPaymentDao;
import com.b2c.prototype.dao.item.ICurrencyDiscountDao;
import com.b2c.prototype.modal.dto.request.RequestCardDto;
import com.b2c.prototype.modal.dto.request.RequestDiscountDto;
import com.b2c.prototype.modal.dto.request.RequestPaymentDto;
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
import com.tm.core.modal.GeneralEntity;
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
    public void savePayment(RequestPaymentDto requestPaymentDto) {
        CreditCard creditCard = null;
        if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(requestPaymentDto.getPaymentMethod())) {
            creditCard = getCard(requestPaymentDto.getCard());
        }
        Map<Class<?>, Object> processResultMap = executeAsyncProcess(requestPaymentDto);
        Payment payment = Payment.builder()
//                .amount(requestPaymentDto.getAmount())
                .creditCard(creditCard)
                .currencyDiscount((CurrencyDiscount) processResultMap.get(CurrencyDiscount.class))
                .paymentMethod((PaymentMethod) processResultMap.get(PaymentMethod.class))
                .build();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, creditCard);
        generalEntity.addEntityPriority(2, payment);
//        super.saveEntity(generalEntity);
    }

    @Override
    public void updatePayment(PaymentDtoUpdate requestPaymentDtoUpdate) {
        RequestPaymentDto requestPaymentDto = requestPaymentDtoUpdate.getNewEntityDto();
        String searchField = requestPaymentDtoUpdate.getSearchField();

        CreditCard creditCard;
        if (PaymentMethodEnum.CARD.name().equalsIgnoreCase(requestPaymentDto.getPaymentMethod())) {
            creditCard = getCard(requestPaymentDto.getCard());
        } else {
            creditCard = null;
        }
        Map<Class<?>, Object> processResultMap = executeAsyncProcess(requestPaymentDto);
        Payment payment = Payment.builder()
//                .amount(requestPaymentDto.getAmount())
                .creditCard(creditCard)
                .currencyDiscount((CurrencyDiscount) processResultMap.get(CurrencyDiscount.class))
                .paymentMethod((PaymentMethod) processResultMap.get(PaymentMethod.class))
                .build();

        GeneralEntity generalEntity = new GeneralEntity();
        generalEntity.addEntityPriority(1, creditCard);
        generalEntity.addEntityPriority(2, payment);

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

    private CreditCard getCard(RequestCardDto requestCardDto) {
        if (requestCardDto != null) {
            return CreditCard.builder()
                    .cardNumber(requestCardDto.getCartNumber())
                    .dateOfExpire(requestCardDto.getDateOfExpire())
                    .cvv(requestCardDto.getCvv())
                    .isActive(CardUtil.isCardActive(requestCardDto.getDateOfExpire()))
                    .ownerName(requestCardDto.getOwnerName())
                    .ownerSecondName(requestCardDto.getOwnerSecondName())
                    .build();
        }
        Parameter parameter = new Parameter("username", "");
        return (CreditCard) cardDao.getOptionalEntity(parameter)
                .orElseThrow(RuntimeException::new);
    }

    private CurrencyDiscount getDiscount(RequestDiscountDto requestDiscountDto) {
        if (requestDiscountDto != null) {
            if (requestDiscountDto.isCurrency()) {
                Parameter parameter1 = new Parameter("currency", true);
                Parameter parameter2 = new Parameter("amount", requestDiscountDto.getAmount());
                return (CurrencyDiscount) discountDao.getOptionalEntity(parameter1, parameter2).orElse(null);
            }
            if (requestDiscountDto.isPercents()) {
                Parameter parameter1 = new Parameter("percents", true);
                Parameter parameter2 = new Parameter("amount", requestDiscountDto.getAmount());
                return (CurrencyDiscount) discountDao.getOptionalEntity(parameter1, parameter2).orElse(null);
            }
        }
        return null;
    }

    private Map<Class<?>, Object> executeAsyncProcess(RequestPaymentDto requestPaymentDto) {
        Task paymentMethodTask = new Task(
                () -> entityCachedMap.getEntity(PaymentMethod.class, "value", requestPaymentDto.getPaymentMethod()),
                PaymentMethod.class
        );
        Task discountTask = new Task(
                () -> getDiscount(requestPaymentDto.getDiscount()),
                CurrencyDiscount.class
        );
        return asyncProcessor.process(paymentMethodTask, discountTask);
    }

    private Map<Class<?>, Object> executeAsyncProcess(PaymentDtoUpdate requestPaymentDtoUpdate) {
        RequestPaymentDto requestPaymentDto = requestPaymentDtoUpdate.getNewEntityDto();
        String searchField = requestPaymentDtoUpdate.getSearchField();
        Task paymentMethodTask = new Task(
                () -> entityCachedMap.getEntity(PaymentMethod.class, "value", requestPaymentDto.getPaymentMethod()),
                PaymentMethod.class
        );
        Task discountTask = new Task(
                () -> getDiscount(requestPaymentDto.getDiscount()),
                CurrencyDiscount.class
        );
        Task paymentTask = new Task(
                () -> {
                    Parameter parameter = new Parameter("order_id", searchField);
                    return paymentDao.getOptionalGeneralEntity(parameter);
                },
                Payment.class
        );
        return asyncProcessor.process(paymentMethodTask, discountTask, paymentTask);
    }

}
