package com.b2c.prototype.modal.dto.payload.item.response;

import com.b2c.prototype.modal.dto.payload.store.AvailabilityStatusDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticularStockQuantityDto {
    private AvailabilityStatusDto availabilityStatus;
    private String countType;
    private int quantity;
}
