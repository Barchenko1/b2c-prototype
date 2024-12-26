package com.b2c.prototype.modal.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ResponseItemDataDto {
    private Map<String, String> description;
    private String categoryName;
    private String itemTypeName;
    private String brandName;
    private String itemStatus;
}
