package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;

import java.util.List;

public interface IUserCreditCardManager {
    void saveUserCreditCardByUserId(String userId, UserCreditCardDto userCreditCardDto);
    void updateUserCreditCardByUserId(String userId, String creditCardNumber, UserCreditCardDto userCreditCardDto);
    void setDefaultUserCreditCard(String userId, String cardNumber);
    void deleteCreditCardByUserId(String userId, String cardNumber);

    UserCreditCardDto getDefaultUserCreditCard(String userId);
    List<UserCreditCardDto> getCreditCardListByUserId(String userId);
    List<UserCreditCardDto> getAllCreditCardByCardNumber(String cardNumber);
}
