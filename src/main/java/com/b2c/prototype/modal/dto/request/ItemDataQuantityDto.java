package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDataQuantityDto {
    private String articularId;
    private String orderId;
    private int quantity;
}
