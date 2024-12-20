package com.b2c.prototype.modal.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class CategoryDto {
    private String name;
    private CategoryDto parent;
    private List<CategoryDto> childNodeList;
}
