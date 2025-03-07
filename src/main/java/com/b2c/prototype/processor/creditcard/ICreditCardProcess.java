package com.b2c.prototype.processor.creditcard;

import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;

import java.util.List;
import java.util.Map;

public interface ICreditCardProcess {
    void saveUpdateCreditCard(Map<String, String> requestParams, CreditCardDto creditCardDto);
    void deleteCreditCard(Map<String, String> requestParams);

    List<ResponseUserCreditCardDto> getCreditCardListByUserId(Map<String, String> requestParams);
    ResponseCreditCardDto getCreditCardByOrderId(Map<String, String> requestParams);
    List<ResponseCreditCardDto> getAllCreditCards(Map<String, String> requestParams);
}
