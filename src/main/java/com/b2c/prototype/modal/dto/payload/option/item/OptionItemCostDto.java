package com.b2c.prototype.modal.dto.payload.option.item;

import com.b2c.prototype.modal.dto.common.AbstractConstantDto;
import com.b2c.prototype.modal.dto.payload.item.PriceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class OptionItemCostDto extends AbstractConstantDto {
    private PriceDto price;
}
