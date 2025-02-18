package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.constant.BrandDto;
import com.b2c.prototype.modal.dto.payload.constant.CategoryValueDto;
import com.b2c.prototype.modal.dto.payload.constant.ItemTypeDto;
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
public class ResponseItemDataDto {
    private String itemId;
    private Map<String, String> description;
    private CategoryValueDto category;
    private ItemTypeDto itemType;
    private BrandDto brand;
    private Set<ResponseArticularDto> articularItemSet;
}
