package com.b2c.prototype.modal.dto.payload;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ItemDataDto {
    private String name;
    private Map<String, String> description;
    private String category;
    private String itemType;
    private String brand;
    private String itemStatus;
    private Set<ItemDataOptionDto> itemDataOptionSet;
}
