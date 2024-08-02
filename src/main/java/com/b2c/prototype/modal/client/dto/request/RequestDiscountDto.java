package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

@Data
public class RequestDiscountDto {
    private int amount;
    private boolean isCurrency;
    private boolean isPercents;
}
