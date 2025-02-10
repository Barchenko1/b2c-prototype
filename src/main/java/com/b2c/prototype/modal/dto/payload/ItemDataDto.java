package com.b2c.prototype.modal.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemDataDto {
    private Map<String, String> description;
    private String category;
    private String itemType;
    private String brand;
    private Set<ArticularItemDto> articularItemSet;
}
