package com.b2c.prototype.modal.dto.payload.store;

import com.b2c.prototype.modal.dto.payload.order.ArticularItemQuantityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticularStockDto {
    private AvailabilityStatusDto availabilityStatus;
    private String countType;
    private List<ArticularItemQuantityDto> articularItemQuantities;
}
