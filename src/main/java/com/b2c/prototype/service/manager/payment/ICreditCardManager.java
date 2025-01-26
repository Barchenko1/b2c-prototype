package com.b2c.prototype.service.manager.payment;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.delete.MultipleFieldsSearchDtoDelete;
import com.b2c.prototype.modal.dto.searchfield.CreditCardSearchFieldEntityDto;
import com.b2c.prototype.modal.dto.response.ResponseCreditCardDto;
import com.b2c.prototype.modal.dto.update.CreditCardSearchFieldEntityUpdateDto;

import java.util.List;

public interface ICreditCardManager {
    void saveCreditCardByUserId(CreditCardSearchFieldEntityDto creditCardSearchFieldEntityDto);
    void saveCreditCardByOrderId(CreditCardSearchFieldEntityDto creditCardSearchFieldEntityDto);
    void updateCreditCardByUserId(CreditCardSearchFieldEntityUpdateDto creditCardSearchFieldEntityUpdateDto);
    void updateCreditCardByOrderId(CreditCardSearchFieldEntityUpdateDto creditCardSearchFieldEntityUpdateDto);
    void deleteCreditCardByUserId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    void deleteCreditCardByOrderId(MultipleFieldsSearchDtoDelete multipleFieldsSearchDtoDelete);
    List<ResponseCreditCardDto> getCardListByUserId(OneFieldEntityDto oneFieldEntityDto);
    ResponseCreditCardDto getCardByOrderId(OneFieldEntityDto oneFieldEntityDto);
    List<ResponseCreditCardDto> getAllCards();
}
