package com.b2c.prototype.modal.dto.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticularItemDto {
    private String productName;
    private Set<SingleOptionItemDto> options;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private String status;
    private InitDiscountDto discount;
}
