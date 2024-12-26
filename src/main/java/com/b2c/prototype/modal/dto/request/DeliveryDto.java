package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryDto {
    private AddressDto deliveryAddress;
    private String deliveryType;
}
