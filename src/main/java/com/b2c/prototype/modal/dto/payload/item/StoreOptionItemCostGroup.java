package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.dto.common.AbstractConstantDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StoreOptionItemCostGroup extends AbstractConstantDto {
    private Map<String, OptionItemCostDto> costOptions;
}
