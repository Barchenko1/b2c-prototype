package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

@Data
public class RequestDeliveryDto {
    private RequestAddressDto deliveryAddressDto;
    private String deliveryType;
}
