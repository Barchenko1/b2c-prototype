package com.b2c.prototype.modal.dto.payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCardDto {
    private String cardNumber;
    private int monthOfExpire;
    private int yearOfExpire;
    private String cvv;
    private String ownerName;
    private String ownerSecondName;
}
