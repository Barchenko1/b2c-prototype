package com.b2c.prototype.processor.user;

import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;

import java.util.List;
import java.util.Map;

public interface IUserCreditCardProcess {
    void saveUpdateUserCreditCardByUserId(Map<String, String> requestParams, UserCreditCardDto userCreditCardDto);
    void deleteUserCreditCard(Map<String, String> requestParams);
    void setDefaultUserCreditCard(Map<String, String> requestParams);
    UserCreditCardDto getDefaultUserCreditCard(Map<String, String> requestParams);
    List<UserCreditCardDto> getUserCreditCardListByUserId(Map<String, String> requestParams);
    List<UserCreditCardDto> getAllCreditCardByCardNumber(Map<String, String> requestParams);
}
