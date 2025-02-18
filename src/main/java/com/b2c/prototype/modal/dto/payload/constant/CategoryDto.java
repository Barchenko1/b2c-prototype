package com.b2c.prototype.modal.dto.payload.constant;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private CategoryDto parent;
    private CategoryValueDto root;
    private List<CategoryDto> childNodeList;
}
