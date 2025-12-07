package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.modal.entity.option.OptionItem;
import com.b2c.prototype.modal.entity.option.OptionItemCost;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class StoreOptionItemGroupTransfer {
    private Map<String, OptionGroup> optionGroup;
    private Map<String, OptionItem> optionItems;
    private Map<String, OptionItemCost> optionItemCosts;
}
