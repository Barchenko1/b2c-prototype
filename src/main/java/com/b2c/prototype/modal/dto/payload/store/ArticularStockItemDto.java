package com.b2c.prototype.modal.dto.payload.store;

import com.b2c.prototype.modal.dto.payload.item.ArticularFullDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticularStockItemDto {
    private ArticularFullDescription articularFullDescription;
    private AvailabilityStatusDto availabilityStatus;
    private String countType;
    private int quantity;
}
