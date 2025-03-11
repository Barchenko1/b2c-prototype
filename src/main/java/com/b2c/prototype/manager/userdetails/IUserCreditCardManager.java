package com.b2c.prototype.manager.userdetails;

import com.b2c.prototype.modal.dto.payload.UserCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;

import java.util.List;

public interface IUserCreditCardManager {
    void saveUserCreditCardByUserId(String userId, UserCreditCardDto userCreditCardDto);
    void updateUserCreditCardByUserId(String userId, String creditCardNumber, UserCreditCardDto userCreditCardDto);
    void setDefaultUserCreditCard(String userId, String cardNumber);
    void deleteCreditCardByUserId(String userId, String cardNumber);

    ResponseUserCreditCardDto getDefaultUserCreditCard(String userId);
    List<ResponseUserCreditCardDto> getCreditCardListByUserId(String userId);
    List<ResponseCreditCardDto> getAllCreditCardByCardNumber(String cardNumber);
}
