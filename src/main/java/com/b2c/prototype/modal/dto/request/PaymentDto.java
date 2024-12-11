package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PaymentDto {
    private String order_id;
    private String paymentMethod;
    private CreditCardDto card;
//    private Discount discount;
    private double amount;
}
