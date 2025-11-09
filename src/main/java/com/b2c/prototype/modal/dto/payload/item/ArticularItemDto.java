package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.dto.payload.discount.DiscountDto;
import com.b2c.prototype.modal.dto.payload.option.item.OptionItemDto;
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
    private Set<OptionItemDto> options;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private String status;
    private DiscountDto discount;
}
