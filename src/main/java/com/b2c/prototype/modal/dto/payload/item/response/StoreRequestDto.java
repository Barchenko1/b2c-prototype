package com.b2c.prototype.modal.dto.payload.item.response;

import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreRequestDto {
    private String storeId;
    private String storeName;
    private boolean isActive;
    private AddressDto address;
    private Map<String, ArticularStockQuantityDto> stock;
}
