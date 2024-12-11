package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreditCardDto {
    private String cardNumber;
    private String dateOfExpire;
    private String cvv;
    private String ownerName;
    private String ownerSecondName;
}
