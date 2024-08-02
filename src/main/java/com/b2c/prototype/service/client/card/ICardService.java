package com.b2c.prototype.service.client.card;

import com.b2c.prototype.modal.client.dto.request.RequestCardDto;
import com.b2c.prototype.modal.client.dto.update.RequestCardDtoUpdate;
import com.b2c.prototype.modal.client.dto.response.ResponseCardDto;

public interface ICardService {
    void saveCard(RequestCardDto card);
    void updateCard(RequestCardDtoUpdate requestCardDtoUpdate);
    void deleteCardByCardNumber(String cardNumber);
    ResponseCardDto getCardByCardOwnerUserName(String cardOwnerUsername);
    ResponseCardDto getCardByCardNumber(String cardNumber);
}
