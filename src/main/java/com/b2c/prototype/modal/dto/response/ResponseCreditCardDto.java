package com.b2c.prototype.modal.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCreditCardDto {
    private String cardNumber;
    private int monthOfExpire;
    private int yearOfExpire;
    private boolean isActive;
    private String ownerName;
    private String ownerSecondName;
}
