package com.b2c.prototype.modal.dto.payload.item.response;

import com.b2c.prototype.modal.dto.payload.item.ArticularGroupDto;
import com.b2c.prototype.modal.dto.payload.item.ArticularItemDto;
import com.b2c.prototype.modal.dto.payload.store.StoreDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreArticularGroupResponseDto {
    private String tenantId;
    private ArticularGroupDto articularGroup;
    private List<ArticularItemDto> articularItems;
    private List<StoreDto> stores;

}
