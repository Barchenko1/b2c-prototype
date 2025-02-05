package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDataOptionDto {
    private OptionItemDto optionItem;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private DiscountDto discount;
}
