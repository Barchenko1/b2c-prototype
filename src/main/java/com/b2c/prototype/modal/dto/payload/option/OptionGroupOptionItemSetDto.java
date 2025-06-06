package com.b2c.prototype.modal.dto.payload.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionGroupOptionItemSetDto {
    private OptionGroupDto optionGroup;
    private Set<OptionItemDto> optionItems;
}
