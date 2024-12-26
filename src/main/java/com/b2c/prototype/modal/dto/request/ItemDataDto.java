package com.b2c.prototype.modal.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDataDto {
    private String name;
    private String description;
    private String categoryName;
    private String itemTypeName;
    private String brandName;
    private String itemStatus;
}
