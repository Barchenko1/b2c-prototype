package com.b2c.prototype.modal.dto.payload.user;

import com.b2c.prototype.modal.dto.payload.order.ResponseCreditCardDto;
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
public class ResponseUserCreditCardDto {
    private ResponseCreditCardDto creditCard;
    private boolean isDefault;
}
