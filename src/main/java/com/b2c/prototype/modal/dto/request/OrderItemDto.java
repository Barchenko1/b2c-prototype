package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderItemDto {
    private List<ItemDto> itemDtoList;
    private DeliveryDto deliveryDto;
    private List<ContactInfoDto> contactInfoDtoList;
    private PaymentDto paymentDto;
    private String note;
}
