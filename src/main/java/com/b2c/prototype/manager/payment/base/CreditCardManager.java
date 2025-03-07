package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;
import com.b2c.prototype.modal.entity.order.OrderArticularItemQuantity;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.dao.payment.ICreditCardDao;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.payment.ICreditCardManager;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
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
    private final IParameterFactory parameterFactory;

    public CreditCardManager(ICreditCardDao creditCardDao,
                             ISearchService searchService,
                             ITransformationFunctionService transformationFunctionService,
                             IParameterFactory parameterFactory) {
        this.entityOperationDao = new EntityOperationManager(creditCardDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUpdateCreditCardByUserId(String userId, CreditCardDto creditCardDto) {
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
    public void saveUpdateCreditCardByOrderId(String orderId, CreditCardDto creditCardDto) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItemQuantity orderArticularItemQuantity = searchService.getNamedQueryEntity(
                    OrderArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
            CreditCard newCreditCard = transformationFunctionService.getEntity(
                    CreditCard.class,
                    creditCardDto);
//            Payment payment = orderArticularItemQuantity.getPayment();
//            payment.setCreditCard(newCreditCard);
//            session.merge(payment);
        });
    }

    @Override
    public void deleteCreditCardByUserId(String userId) {
        entityOperationDao.executeConsumer(session -> {
            UserDetails userDetails = searchService.getNamedQueryEntity(
                    UserDetails.class,
                    "",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserCreditCard creditCard = userDetails.getUserCreditCardList().stream()
                    .filter(existingUserCreditCard ->
                            existingUserCreditCard.getCreditCard().getCardNumber().equals(userId))
                    .findFirst()
                    .orElseThrow(() ->new RuntimeException("User has no credit card"));
            session.remove(creditCard);
        });
    }

    @Override
    public void deleteCreditCardByOrderId(String orderId) {
        entityOperationDao.executeConsumer(session -> {
            OrderArticularItemQuantity orderItemDataOption = searchService.getNamedQueryEntity(
                    OrderArticularItemQuantity.class,
                    "",
                    parameterFactory.createStringParameter(ORDER_ID, orderId));
//            CreditCard creditCard = orderItemDataOption.getPayment().getCreditCard();
//            if (!creditCard.getCardNumber().equals(multipleFieldsSearchDtoDelete.getInnerSearchField())) {
//                throw new RuntimeException("User has no credit card");
//            }
//            session.remove(creditCard);
        });
    }

    @Override
    public List<ResponseUserCreditCardDto> getCreditCardListByUserId(String userId) {
        return entityOperationDao.getSubNamedQueryEntityDtoList(
                "",
                parameterFactory.createStringParameter(USER_ID, userId),
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseUserCreditCardDto.class));
    }

    @Override
    public ResponseCreditCardDto getCreditCardByOrderId(String orderId) {
        return entityOperationDao.getNamedQueryEntityDto(
                "",
                parameterFactory.createStringParameter(ORDER_ID, orderId),
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

    @Override
    public List<ResponseCreditCardDto> getAllCreditCards() {
        return entityOperationDao.getNamedQueryEntityDtoList(
                "",
                transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class));
    }

}
