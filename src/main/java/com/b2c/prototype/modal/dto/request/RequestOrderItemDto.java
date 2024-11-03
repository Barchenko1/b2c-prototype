package com.b2c.prototype.modal.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderItemDto {
    private List<RequestItemDto> requestItemDtoList;
    private RequestDeliveryDto requestDeliveryDto;
    private List<RequestContactInfoDto> requestContactInfoDtoList;
    private RequestPaymentDto requestPaymentDto;
    private String note;
}
