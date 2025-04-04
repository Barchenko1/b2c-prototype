package com.b2c.prototype.processor.creditcard;

import com.b2c.prototype.manager.userdetails.IUserCreditCardManager;
import com.b2c.prototype.modal.dto.payload.user.UserCreditCardDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.payload.user.ResponseUserCreditCardDto;

import java.util.List;
import java.util.Map;

public class UserCreditCardProcess implements IUserCreditCardProcess {

    private final IUserCreditCardManager creditCardManager;

    public UserCreditCardProcess(IUserCreditCardManager creditCardManager) {
        this.creditCardManager = creditCardManager;
    }

    @Override
    public void saveUpdateUserCreditCardByUserId(Map<String, String> requestParams, UserCreditCardDto userCreditCardDto) {
        String userId = requestParams.get("userId");
        String cardNumber = requestParams.get("cardNumber");
        if (cardNumber != null) {
            creditCardManager.updateUserCreditCardByUserId(userId, cardNumber, userCreditCardDto);
        } else {
            creditCardManager.saveUserCreditCardByUserId(userId, userCreditCardDto);
        }
    }

    @Override
    public void setDefaultUserCreditCard(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String cardNumber = requestParams.get("cardNumber");
        creditCardManager.setDefaultUserCreditCard(userId, cardNumber);
    }

    @Override
    public void deleteUserCreditCard(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        String cardNumber = requestParams.get("cardNumber");
        creditCardManager.deleteCreditCardByUserId(userId, cardNumber);
    }

    @Override
    public ResponseUserCreditCardDto getDefaultUserCreditCard(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return creditCardManager.getDefaultUserCreditCard(userId);
    }

    @Override
    public List<ResponseUserCreditCardDto> getUserCreditCardListByUserId(Map<String, String> requestParams) {
        String userId = requestParams.get("userId");
        return creditCardManager.getCreditCardListByUserId(userId);
    }

    @Override
    public List<ResponseCreditCardDto> getAllCreditCardByCardNumber(Map<String, String> requestParams) {
        String cardNumber = requestParams.get("cardNumber");
        return creditCardManager.getAllCreditCardByCardNumber(cardNumber);
    }
}
