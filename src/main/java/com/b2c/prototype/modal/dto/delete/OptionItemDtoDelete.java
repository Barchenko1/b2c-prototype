package com.b2c.prototype.modal.dto.delete;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OptionItemDtoDelete {
    private String optionItemValue;
    private String searchField;
}
