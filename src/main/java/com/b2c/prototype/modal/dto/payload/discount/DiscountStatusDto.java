package com.b2c.prototype.modal.dto.payload.discount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiscountStatusDto {
    private String charSequenceCode;
    private boolean isActive;
}
