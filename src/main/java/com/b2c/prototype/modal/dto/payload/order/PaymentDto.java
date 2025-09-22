package com.b2c.prototype.modal.dto.payload.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {
    private String paymentMethod;
    private CreditCardDto creditCard;
    private String discountCharSequenceCode;
}
