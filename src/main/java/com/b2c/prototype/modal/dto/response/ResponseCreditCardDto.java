package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseCreditCardDto {
    private String cardNumber;
    private String dateOfExpire;
    private boolean isActive;
    private String ownerName;
    private String ownerSecondName;
}
