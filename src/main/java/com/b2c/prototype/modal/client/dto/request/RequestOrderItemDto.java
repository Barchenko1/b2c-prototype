package com.b2c.prototype.modal.client.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class RequestOrderItemDto {
    private List<RequestItemDto> requestItemDtoList;

}
