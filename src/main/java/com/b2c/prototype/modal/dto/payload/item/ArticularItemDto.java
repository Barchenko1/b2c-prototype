package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.dto.payload.discount.InitDiscountDto;
import com.b2c.prototype.modal.dto.payload.option.SingleOptionItemDto;
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
    private String articularId;
    private String productName;
    private Set<SingleOptionItemDto> options;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private String status;
    private InitDiscountDto discount;
}
