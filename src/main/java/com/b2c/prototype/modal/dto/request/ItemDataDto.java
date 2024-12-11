package com.b2c.prototype.modal.dto.request;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ItemDataDto {
    private String name;
    private String description;
    private String categoryName;
    private String itemTypeName;
    private String brandName;
    private String itemStatus;
    private PriceDto price;
    private Map<OneFieldEntityDto, Set<OneFieldEntityDto>> optionGroupOptionItemMap;
    private CurrencyDiscountDto currencyDiscount;
    private PercentDiscountDto percentDiscount;
}
