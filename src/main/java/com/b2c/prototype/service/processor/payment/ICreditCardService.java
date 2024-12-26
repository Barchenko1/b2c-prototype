package com.b2c.prototype.service.processor.payment;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.request.CreditCardDtoSearchField;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.update.CreditCardDtoUpdate;

import java.util.List;

public interface ICreditCardService {
    void saveCreditCardByUserId(CreditCardDtoSearchField creditCardDtoSearchField);
    void saveCreditCardByOrderId(CreditCardDtoSearchField creditCardDtoSearchField);
    void updateCreditCardByUserId(CreditCardDtoUpdate creditCardDtoUpdate);
    void updateCreditCardByOrderId(CreditCardDtoUpdate creditCardDtoUpdate);
    void deleteCreditCardByUserId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    void deleteCreditCardByOrderId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    List<ResponseCreditCardDto> getCardListByUserId(OneFieldEntityDto oneFieldEntityDto);
    ResponseCreditCardDto getCardByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseCreditCardDto> getAllCards();
}
