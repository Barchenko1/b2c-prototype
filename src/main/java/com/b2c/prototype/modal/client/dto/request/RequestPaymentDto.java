package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

@Data
public class RequestPaymentDto {
    private String order_id;
    private String paymentMethod;
    private RequestCardDto card;
    private RequestDiscountDto discount;
    private double amount;

}
