package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;

import java.util.List;

public interface ICreditCardManager {
    void saveCreditCardByUserId(String userId, CreditCardDto creditCardDto);
    void saveCreditCardByOrderId(String orderId, CreditCardDto creditCardDto);
    void updateCreditCardByUserId(String userId, CreditCardDto creditCardDto);
    void updateCreditCardByOrderId(String orderId, CreditCardDto creditCardDto);
    void deleteCreditCardByUserId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    void deleteCreditCardByOrderId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    List<ResponseCreditCardDto> getCardListByUserId(String userId);
    ResponseCreditCardDto getCardByOrderId(String orderId);
    List<ResponseCreditCardDto> getAllCards();
}
