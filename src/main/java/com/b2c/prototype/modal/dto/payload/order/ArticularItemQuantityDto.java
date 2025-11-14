package com.b2c.prototype.modal.dto.payload.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticularItemQuantityDto {
    private String articularGroupId;
    private String articularId;
    private int quantity;
}
