package com.b2c.prototype.modal.dto.request;

import lombok.Data;

@Data
public class RequestDeliveryDto {
    private RequestAddressDto deliveryAddressDto;
    private String deliveryType;
}
