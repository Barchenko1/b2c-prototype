package com.b2c.prototype.modal.dto.payload.item.request;

import com.b2c.prototype.modal.dto.payload.constant.CategoryCascade;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticularGroupRequestDto {
    private String articularGroupId;
    private Map<String, String> description;
    private CategoryCascade category;
    private Map<String, ArticularItemAssignmentDto> articularItems;
}
