package com.b2c.prototype.modal.dto.payload.item.request;

import com.b2c.prototype.modal.dto.payload.item.StoreDiscountGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemCostGroup;
import com.b2c.prototype.modal.dto.payload.item.StoreOptionItemGroup;
import com.b2c.prototype.modal.dto.payload.item.response.StoreRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreArticularGroupRequestDto {
    private String tenantId;
    private Map<String, StoreDiscountGroup> discountGroups;
    private Map<String, StoreOptionItemGroup> optionItemGroups;
    private Map<String, StoreOptionItemCostGroup> optionItemCostGroups;
    private Map<String, StoreRequestDto> storeGroup;
    private ArticularGroupRequestDto articularGroup;
}
