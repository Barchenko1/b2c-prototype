package com.b2c.prototype.processor.creditcard;

import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.payload.UserCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;

import java.util.List;
import java.util.Map;

public interface IUserCreditCardProcess {
    void saveUpdateUserCreditCardByUserId(Map<String, String> requestParams, UserCreditCardDto userCreditCardDto);
    void deleteUserCreditCard(Map<String, String> requestParams);
    void setDefaultUserCreditCard(Map<String, String> requestParams);
    ResponseUserCreditCardDto getDefaultUserCreditCard(Map<String, String> requestParams);
    List<ResponseUserCreditCardDto> getUserCreditCardListByUserId(Map<String, String> requestParams);
    List<ResponseCreditCardDto> getAllCreditCardByCardNumber(Map<String, String> requestParams);
}
