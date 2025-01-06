package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleOptionItem {
    private String optionItemValue;
    private String optionGroupValue;
}
