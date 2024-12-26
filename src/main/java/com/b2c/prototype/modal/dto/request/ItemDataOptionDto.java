package com.b2c.prototype.modal.dto.request;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ItemDataOptionDto {
    private Map<OneFieldEntityDto, Set<OneFieldEntityDto>> optionGroupOptionItemMap;
    private ItemDataDto itemData;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private DiscountDto discount;
}
