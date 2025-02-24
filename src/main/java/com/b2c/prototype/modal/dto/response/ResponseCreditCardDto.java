package com.b2c.prototype.modal.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseCreditCardDto {
    private String cardNumber;
    private int monthOfExpire;
    private int yearOfExpire;
    private boolean isActive;
    private String ownerName;
    private String ownerSecondName;
}
