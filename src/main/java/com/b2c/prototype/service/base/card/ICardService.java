package com.b2c.prototype.service.base.card;

import com.b2c.prototype.modal.dto.request.RequestCardDto;
import com.b2c.prototype.modal.dto.update.RequestCardDtoUpdate;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;

public interface ICardService {
    void saveCard(RequestCardDto card);
    void updateCard(RequestCardDtoUpdate requestCardDtoUpdate);
    void deleteCardByCardNumber(String cardNumber);
    ResponseCardDto getCardByCardOwnerUserName(String cardOwnerUsername);
    ResponseCardDto getCardByCardNumber(String cardNumber);
}
