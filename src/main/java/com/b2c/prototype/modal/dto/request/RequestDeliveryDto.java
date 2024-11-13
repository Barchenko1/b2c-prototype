package com.b2c.prototype.modal.dto.request;

import lombok.Data;

@Data
public class RequestDeliveryDto {
    private AddressDto deliveryAddressDto;
    private String deliveryType;
}
