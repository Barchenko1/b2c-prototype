package com.b2c.prototype.modal.dto.payload.item.response;

import com.b2c.prototype.modal.dto.payload.constant.CategoryCascade;
import com.b2c.prototype.modal.dto.payload.item.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticularGroupResponseDto {
    private String articularGroupId;
    private Map<String, String> description;
    private CategoryCascade category;
    private List<ItemDto> items;
}
