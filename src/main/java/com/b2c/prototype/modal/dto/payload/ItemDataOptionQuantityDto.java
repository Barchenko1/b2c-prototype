package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDataOptionQuantityDto {
    private String articularId;
    private String orderId;
    private int quantity;
}
