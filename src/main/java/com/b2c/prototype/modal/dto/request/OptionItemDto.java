package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
@Builder
public class OptionItemDto {
    private Map<String, Set<String>> optionGroupOptionItemsMap;
}
