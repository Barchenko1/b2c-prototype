package com.b2c.prototype.modal.dto.payload.option;

import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionItemCostDto {
    private String label;
    private String value;
    private PriceDto price;
}
