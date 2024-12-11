package com.b2c.prototype.service.processor.payment;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CreditCardDto;
import com.b2c.prototype.modal.dto.response.ResponseCardDto;
import com.b2c.prototype.modal.dto.update.CreditCardDtoUpdate;

import java.util.List;

public interface ICreditCardService {
    void saveCreditCard(CreditCardDto card);
    void updateCreditCard(CreditCardDtoUpdate creditCardDtoUpdate);
    void deleteCreditCardByEmail(OneFieldEntityDto oneFieldEntityDto);
    void deleteCreditCardByOrderId(OneFieldEntityDto oneFieldEntityDto);
    ResponseCardDto getCardByEmail(OneFieldEntityDto oneFieldEntityDto);
    ResponseCardDto getCardByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseCardDto> getAllCards();
}
