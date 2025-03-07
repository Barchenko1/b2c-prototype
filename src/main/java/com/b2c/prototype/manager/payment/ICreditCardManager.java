package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;

import java.util.List;

public interface ICreditCardManager {
    void saveUpdateCreditCardByUserId(String userId, CreditCardDto creditCardDto);
    void saveUpdateCreditCardByOrderId(String orderId, CreditCardDto creditCardDto);
    void deleteCreditCardByUserId(String userId);
    void deleteCreditCardByOrderId(String orderId);

    List<ResponseUserCreditCardDto> getCreditCardListByUserId(String userId);
    ResponseCreditCardDto getCreditCardByOrderId(String orderId);
    List<ResponseCreditCardDto> getAllCreditCards();
}
