package com.b2c.prototype.modal.dto.payload.store;

import com.b2c.prototype.modal.dto.payload.order.AddressDto;
import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStoreDto {
    private String storeName;
    private String storeId;
    private AddressDto address;
    private List<ArticularItemQuantityDto> articularItemQuantityList;

}
