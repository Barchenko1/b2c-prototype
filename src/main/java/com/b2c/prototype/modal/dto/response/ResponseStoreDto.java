package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.payload.AddressDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemQuantityDto;
import com.b2c.prototype.modal.dto.payload.ItemDataDto;
import com.b2c.prototype.modal.dto.payload.ArticularItemDto;
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
