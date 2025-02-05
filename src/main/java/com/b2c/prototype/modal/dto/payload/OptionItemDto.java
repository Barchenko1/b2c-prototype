package com.b2c.prototype.modal.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OptionItemDto {
    private ConstantPayloadDto optionGroup;
    private Set<ConstantPayloadDto> optionItems;
}
