package com.b2c.prototype.modal.dto.payload;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ItemDataOptionDto {
    private Map<OneFieldEntityDto, OneFieldEntityDto> optionGroupOptionItemMap;
    private ItemDataDto itemData;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private DiscountDto discount;
}
