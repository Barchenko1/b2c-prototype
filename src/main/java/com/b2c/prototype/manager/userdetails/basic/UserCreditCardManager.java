package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserCreditCardDto;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.userdetails.IUserCreditCardManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;

public class UserCreditCardManager implements IUserCreditCardManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreditCardManager.class);

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public UserCreditCardManager(ITransactionEntityDao creditCardDao,
                                 IQueryService queryService,
                                 IFetchHandler fetchHandler,
                                 ITransformationFunctionService transformationFunctionService,
                                 IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(creditCardDao);
        this.queryService = queryService;
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void saveUserCreditCardByUserId(String userId, UserCreditCardDto userCreditCardDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = queryService.getNamedQueryEntity(
                    session,
                    UserDetails.class,
                    "UserDetails.findUserCreditCardsByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserCreditCard newUserCreditCard = transformationFunctionService.getEntity(UserCreditCard.class, userCreditCardDto);
            boolean isAllCreditCardsFalse = userDetails.getUserCreditCards().stream()
                    .noneMatch(UserCreditCard::isDefault);

            if (userDetails.getUserCreditCards().isEmpty() || isAllCreditCardsFalse) {
                newUserCreditCard.setDefault(true);
            }
            if (newUserCreditCard.isDefault()) {
                userDetails.getUserCreditCards().forEach(userCreditCard -> {
                    userCreditCard.setDefault(false);
                });
            }

            userDetails.getUserCreditCards().add(newUserCreditCard);
            session.merge(userDetails);
        });
    }

    @Override
    public void updateUserCreditCardByUserId(String userId, String creditCardNumber, UserCreditCardDto userCreditCardDto) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = queryService.getNamedQueryEntity(
                    session,
                    UserDetails.class,
                    "UserDetails.findUserCreditCardsByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserCreditCard newUserCreditCard = transformationFunctionService.getEntity(UserCreditCard.class, userCreditCardDto);
            boolean isAllCreditCardsFalse = userDetails.getUserCreditCards().stream()
                    .noneMatch(UserCreditCard::isDefault);

            if (userDetails.getUserCreditCards().isEmpty() || isAllCreditCardsFalse) {
                newUserCreditCard.setDefault(true);
            }
            if (newUserCreditCard.isDefault()) {
                userDetails.getUserCreditCards().forEach(userCreditCard -> {
                    userCreditCard.setDefault(false);
                });
            }

            UserCreditCard existingUserCreditCard = userDetails.getUserCreditCards().stream()
                    .filter(card -> creditCardNumber.equals(card.getCreditCard().getCardNumber()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Credit Card not found"));
            existingUserCreditCard.setDefault(newUserCreditCard.isDefault());
            existingUserCreditCard.getCreditCard().setCardNumber(existingUserCreditCard.getCreditCard().getCardNumber());
            existingUserCreditCard.getCreditCard().setMonthOfExpire(existingUserCreditCard.getCreditCard().getMonthOfExpire());
            existingUserCreditCard.getCreditCard().setYearOfExpire(existingUserCreditCard.getCreditCard().getYearOfExpire());
            existingUserCreditCard.getCreditCard().setCvv(existingUserCreditCard.getCreditCard().getCvv());
            existingUserCreditCard.getCreditCard().setOwnerName(existingUserCreditCard.getCreditCard().getOwnerName());
            existingUserCreditCard.getCreditCard().setOwnerSecondName(existingUserCreditCard.getCreditCard().getOwnerSecondName());
            existingUserCreditCard.getCreditCard().setActive(existingUserCreditCard.getCreditCard().isActive());

            if (userDetails.getUserCreditCards().stream().noneMatch(UserCreditCard::isDefault)) {
                existingUserCreditCard.setDefault(false);
            }

            session.merge(userDetails);
        });
    }

    @Override
    public void setDefaultUserCreditCard(String userId, String cardNumber) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = queryService.getNamedQueryEntity(
                    session,
                    UserDetails.class,
                    "UserDetails.findUserCreditCardsByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));

            UserCreditCard userCreditCard = userDetails.getUserCreditCards().stream()
                    .filter(card -> cardNumber.equals(card.getCreditCard().getCardNumber()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Credit Card not found"));
            userDetails.getUserCreditCards().stream()
                    .filter(card -> !userCreditCard.getCreditCard().equals(card.getCreditCard()))
                    .forEach(card -> card.setDefault(false));

            userCreditCard.setDefault(true);
            session.merge(userDetails);
        });
    }

    @Override
    public void deleteCreditCardByUserId(String userId, String cardNumber) {
        entityOperationManager.executeConsumer(session -> {
            UserDetails userDetails = queryService.getNamedQueryEntity(
                    session,
                    UserDetails.class,
                    "UserDetails.findUserCreditCardsByUserId",
                    parameterFactory.createStringParameter(USER_ID, userId));
            UserCreditCard userCreditCard = userDetails.getUserCreditCards().stream()
                    .filter(existingUserCreditCard ->
                            existingUserCreditCard.getCreditCard().getCardNumber().equals(cardNumber))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("User has no such credit card"));
            userDetails.getUserCreditCards().remove(userCreditCard);
            if (userCreditCard.isDefault()) {
                userDetails.getUserCreditCards().stream()
                        .filter(card -> !card.equals(userCreditCard))
                        .max(Comparator.comparing(c -> c.getCreditCard().getCardNumber()))
                        .ifPresent(lastCard -> lastCard.setDefault(true));
            }

            session.merge(userDetails);
        });
    }

    @Override
    public ResponseUserCreditCardDto getDefaultUserCreditCard(String userId) {
        UserDetails userDetails = fetchHandler.getNamedQueryEntityClose(
                UserDetails.class,
                "UserDetails.findUserCreditCardsByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));

        return userDetails.getUserCreditCards().stream()
                .filter(UserCreditCard::isDefault)
                .map(transformationFunctionService.getTransformationFunction(UserCreditCard.class, ResponseUserCreditCardDto.class))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no such default credit card"));
    }

    @Override
    public List<ResponseUserCreditCardDto> getCreditCardListByUserId(String userId) {
        UserDetails userDetails = fetchHandler.getNamedQueryEntityClose(
                UserDetails.class,
                "UserDetails.findUserCreditCardsByUserId",
                parameterFactory.createStringParameter(USER_ID, userId));

        return userDetails.getUserCreditCards().stream()
                .map(transformationFunctionService.getTransformationFunction(UserCreditCard.class, ResponseUserCreditCardDto.class))
                .toList();
    }

    @Override
    public List<ResponseCreditCardDto> getAllCreditCardByCardNumber(String cardNumber) {
        List<CreditCard> creditCards = fetchHandler.getNamedQueryEntityListClose(
                CreditCard.class,
                "UserCreditCard.findByCreditCardNumber",
                parameterFactory.createStringParameter("cardNumber", cardNumber));
        return creditCards.stream()
                .map(transformationFunctionService.getTransformationFunction(CreditCard.class, ResponseCreditCardDto.class))
                .toList();
    }

}
