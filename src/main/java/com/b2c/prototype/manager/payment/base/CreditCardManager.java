package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItem;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.modal.entity.payment.Payment;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.payment.ICreditCardManager;
import com.b2c.prototype.service.common.EntityOperationManager;
import com.b2c.prototype.service.common.IEntityOperationManager;
import com.b2c.prototype.service.query.ISearchService;
import com.b2c.prototype.service.supplier.ISupplierService;
import com.tm.core.finder.factory.IParameterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.b2c.prototype.util.Constant.ORDER_ID;
import static com.b2c.prototype.util.Constant.USER_ID;

public class CreditCardManager implements ICreditCardManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardManager.class);

    private final IEntityOperationManager entityOperationDao;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final ISupplierService supplierService;
    private final IParameterFactory parameterFactory;

    public CreditCardManager(ICreditCardDao creditCardDao,
                             ISearchService searchService,
                             ITransformationFunctionService transformationFunctionService,
                             ISupplierService supplierService,
                             IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(creditCardDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.supplierService = supplierService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveCreditCardByUserId(String userId, CreditCardDto creditCardDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "",
                    parameterFactory.createStringParameter(USER_ID, userId));
            CreditCard creditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardDto);
            Set<UserCreditCard> creditCardList = Optional.ofNullable(userDetails.getUserCreditCardList())
                    .orElseThrow(() -> new RuntimeException("User has no credit card list"));
            if (creditCardList.contains(creditCard)) {
                throw new RuntimeException("User already has this credit card");
            }
//            creditCardList.add(creditCard);
            session.merge(userDetails);
        });
    }

    @Override
    public void updateCreditCardByUserId(String userId, CreditCardDto creditCardDto) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "",
                    parameterFactory.createStringParameter(USER_ID, userId));
            CreditCard newCreditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardDto);
//            CreditCard existingCreditCard = userDetails.getCreditCardList().stream()
//                    .filter(card -> card.getCardNumber().equals(creditCardDto.getCardNumber()))
//                    .findFirst()
//                    .orElseThrow();
//            newCreditCard.setId(existingCreditCard.getId());
            session.merge(newCreditCard);
        });
    }

    @Override
    public void saveCreditCardByOrderId(String orderId, CreditCardDto creditCardDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getNamedQueryEntity(
                    OrderArticularItem.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            CreditCard newCreditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardDto);
            Payment payment = orderItemDataOption.getPayment();
            payment.setCreditCard(newCreditCard);
            session.merge(payment);
        });
    }

    @Override
    public void updateCreditCardByOrderId(String orderId, CreditCardDto creditCardDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getNamedQueryEntity(
                    OrderArticularItem.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            CreditCard newCreditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardDto);
            Payment payment = orderItemDataOption.getPayment();
            CreditCard existingCreditCard = payment.getCreditCard();
            if (!existingCreditCard.getCardNumber().equals(newCreditCard.getCardNumber())) {
                throw new RuntimeException("Card number mismatch");
            }
            payment.setCreditCard(newCreditCard);
            session.merge(payment);
        });
    }

    @Override
    public void deleteCreditCardByUserId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "",
                    parameterFactory.createStringParameter(USER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()));
            UserCreditCard creditCard = userDetails.getUserCreditCardList().stream()
                    .filter(existingUserCreditCard ->
                            existingUserCreditCard.getCreditCard().getCardNumber().equals(multipleFieldsSearchDtoDelete.getInnerSearchField()))
                    .findFirst()
                    .orElseThrow(() ->new RuntimeException("User has no credit card"));
            session.remove(creditCard);
        });
    }

    @Override
    public void deleteCreditCardByOrderId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItem orderItemDataOption = searchService.getNamedQueryEntity(
                    OrderArticularItem.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, multipleFieldsSearchDtoDelete.getMainSearchField()));
            CreditCard creditCard = orderItemDataOption.getPayment().getCreditCard();
            if (!creditCard.getCardNumber().equals(multipleFieldsSearchDtoDelete.getInnerSearchField())) {
                throw new RuntimeException("User has no credit card");
            }
            session.remove(creditCard);
        });
    }

    @Override
    public List<ResponseCreditCardDto> getCardListByUserId(String userId) {
        return entityOperationDao.getSubGraphEntityDtoList(
                "",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

    @Override
    public ResponseCreditCardDto getCardByOrderId(String orderId) {
        return entityOperationDao.getGraphEntityDto(
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

    @Override
    public List<ResponseCreditCardDto> getAllCards() {
        return entityOperationDao.getGraphEntityDtoList("",
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

}
