package com.b2c.prototype.modal.dto.payload.item;

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
public class ArticularFullDescription {
    private String articularGroupId;
    private Map<String, String> description;
    private CategoryCascade category;
    private ArticularItemDto articularItem;
}
