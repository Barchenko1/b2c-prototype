package com.b2c.prototype.modal.dto.payload.option;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SingleOptionItemDto {
    private OptionGroupDto optionGroup;
    private OptionItemDto optionItem;
}
