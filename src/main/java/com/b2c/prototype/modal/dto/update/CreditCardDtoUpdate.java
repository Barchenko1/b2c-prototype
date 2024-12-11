package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.request.CreditCardDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardDtoUpdate {
    private String oldCardNumber;
    private String searchField;
    private CreditCardDto newCreditCard;
}
