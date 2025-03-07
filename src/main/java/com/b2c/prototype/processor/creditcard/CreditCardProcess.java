package com.b2c.prototype.processor.creditcard;

import com.b2c.prototype.manager.payment.ICreditCardManager;
import com.b2c.prototype.modal.dto.payload.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseUserCreditCardDto;

import java.util.List;
import java.util.Map;

public class CreditCardProcess implements ICreditCardProcess {

    private final ICreditCardManager creditCardManager;

    public CreditCardProcess(ICreditCardManager creditCardManager) {
        this.creditCardManager = creditCardManager;
    }

    @Override
    public void saveUpdateCreditCard(Map<String, String> requestParams, CreditCardDto creditCardDto) {
        String userId = requestParams.get("userId");
        String cardNumber = requestParams.get("cardNumber");
        String orderId = requestParams.get("orderId");

        if (orderId != null) {
            creditCardManager.saveUpdateCreditCardByOrderId(orderId, creditCardDto);
        } else if (userId != null && cardNumber != null) {
            creditCardManager.saveUpdateCreditCardByUserId(userId, creditCardDto);
        }
    }

    @Override
    public void deleteCreditCard(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String cardNumber = requestParams.get("cardNumber");
        String orderId = requestParams.get("orderId");
        if (orderId != null) {
            creditCardManager.deleteCreditCardByOrderId(orderId);
        } else if (userId != null && cardNumber != null) {
            creditCardManager.deleteCreditCardByUserId(userId);
        }
    }

    @Override
    public List<ResponseUserCreditCardDto> getCreditCardListByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return creditCardManager.getCreditCardListByUserId(userId);
    }

    @Override
    public ResponseCreditCardDto getCreditCardByOrderId(Map<String, String> requestParams) {
        String orderId = requestParams.get("orderId");
        return creditCardManager.getCreditCardByOrderId(orderId);
    }

    @Override
    public List<ResponseCreditCardDto> getAllCreditCards(Map<String, String> requestParams) {
        return creditCardManager.getAllCreditCards();
    }
}
