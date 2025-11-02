package com.b2c.prototype.manager.userdetails.basic;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.entity.payment.CreditCard;
import com.b2c.prototype.modal.entity.user.UserCreditCard;
import com.b2c.prototype.modal.entity.user.UserDetails;
import com.b2c.prototype.manager.userdetails.IUserCreditCardManager;
import com.b2c.prototype.transform.userdetails.IUserDetailsTransformService;
import com.nimbusds.jose.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static com.b2c.prototype.util.Constant.USER_ID;

@Service
public class UserCreditCardManager implements IUserCreditCardManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserCreditCardManager.class);

    private final IGeneralEntityDao generalEntityDao;
    private final IUserDetailsTransformService userDetailsTransformService;

    public UserCreditCardManager(IGeneralEntityDao generalEntityDao, IUserDetailsTransformService userDetailsTransformService) {
        this.generalEntityDao = generalEntityDao;
        this.userDetailsTransformService = userDetailsTransformService;
    }

    @Override
    public void saveUserCreditCardByUserId(String userId, UserCreditCardDto userCreditCardDto) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findUserCreditCardsByUserId",
                Pair.of(USER_ID, userId));
        UserCreditCard newUserCreditCard = userDetailsTransformService.mapUserCreditCardDtoToUserCreditCard(userCreditCardDto);
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
        generalEntityDao.mergeEntity(userDetails);
    }

    @Override
    public void updateUserCreditCardByUserId(String userId, String creditCardNumber, UserCreditCardDto userCreditCardDto) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findUserCreditCardsByUserId",
                Pair.of(USER_ID, userId));
        UserCreditCard newUserCreditCard = userDetailsTransformService.mapUserCreditCardDtoToUserCreditCard(userCreditCardDto);
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

        generalEntityDao.mergeEntity(userDetails);
    }

    @Override
    public void setDefaultUserCreditCard(String userId, String cardNumber) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findUserCreditCardsByUserId",
                Pair.of(USER_ID, userId));

        UserCreditCard userCreditCard = userDetails.getUserCreditCards().stream()
                .filter(card -> cardNumber.equals(card.getCreditCard().getCardNumber()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Credit Card not found"));
        userDetails.getUserCreditCards().stream()
                .filter(card -> !userCreditCard.getCreditCard().equals(card.getCreditCard()))
                .forEach(card -> card.setDefault(false));

        userCreditCard.setDefault(true);
        generalEntityDao.mergeEntity(userDetails);
    }

    @Override
    public void deleteCreditCardByUserId(String userId, String cardNumber) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findUserCreditCardsByUserId",
                Pair.of(USER_ID, userId));
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

        generalEntityDao.mergeEntity(userDetails);
    }

    @Override
    public UserCreditCardDto getDefaultUserCreditCard(String userId) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findUserCreditCardsByUserId",
                Pair.of(USER_ID, userId));

        return userDetails.getUserCreditCards().stream()
                .filter(UserCreditCard::isDefault)
                .map(userDetailsTransformService::mapUserCreditCardToUserCreditCardDto)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User has no such default credit card"));
    }

    @Override
    public List<UserCreditCardDto> getCreditCardListByUserId(String userId) {
        UserDetails userDetails = generalEntityDao.findEntity(
                "UserDetails.findUserCreditCardsByUserId",
                Pair.of(USER_ID, userId));

        return userDetails.getUserCreditCards().stream()
                .map(userDetailsTransformService::mapUserCreditCardToUserCreditCardDto)
                .toList();
    }

    @Override
    public List<UserCreditCardDto> getAllCreditCardByCardNumber(String cardNumber) {
        List<CreditCard> creditCards = generalEntityDao.findEntityList(
                "UserCreditCard.findByCreditCardNumber",
                Pair.of("cardNumber", cardNumber));

        return List.of();
//        return creditCards.stream()
//                .map(userDetailsTransformService::mapUserCreditCardToUserCreditCardDto)
//                .toList();
    }

}
