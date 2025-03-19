package com.b2c.prototype.modal.dto.payload.constant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
    private String label;
    private String value;
    private String oldValue;
    private List<CategoryDto> childList;
}
