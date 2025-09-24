package com.b2c.prototype.modal.dto.payload.option;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OptionGroupDto {
    private String label;
    private String value;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Set<OptionItemDto> optionItems;
}
