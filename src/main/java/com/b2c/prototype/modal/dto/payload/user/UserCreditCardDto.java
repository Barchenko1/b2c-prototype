package com.b2c.prototype.modal.dto.payload.user;

import com.b2c.prototype.modal.dto.payload.order.CreditCardDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class UserCreditCardDto {
    private CreditCardDto creditCard;
    @JsonProperty("isDefault")
    private boolean isDefault;
}
