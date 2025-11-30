package com.b2c.prototype.modal.dto.payload.item;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreArticularGroupRequestDto {
    private String region;
    private Map<String, StoreDiscountGroup> discountGroups;
    private Map<String, StoreOptionItemGroup> optionItemGroups;
    private Map<String, StoreOptionItemCostGroup> optionItemCostGroups;
    private ArticularGroupDto articularGroup;
    private Map<String, ArticularItemAssignmentDto> articularItems;
    private Map<String, List<StoreRequestDto>> stores;
}
