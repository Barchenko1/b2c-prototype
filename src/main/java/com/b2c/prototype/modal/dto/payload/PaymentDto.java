package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentDto {
    private String orderId;
    private String paymentMethod;
    private CreditCardDto creditCard;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private DiscountDto discount;
}
