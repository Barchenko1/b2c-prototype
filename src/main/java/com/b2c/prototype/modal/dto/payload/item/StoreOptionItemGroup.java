package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.dto.common.AbstractConstantDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemCostDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StoreOptionItemGroup extends AbstractConstantDto {
    private Map<String, OptionItemDto> optionItems;
    private Map<String, OptionItemCostDto> optionItemCosts;
}
