package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.request.CurrencyDiscountDto;
import com.b2c.prototype.modal.dto.request.PercentDiscountDto;
import com.b2c.prototype.modal.dto.request.PriceDto;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ResponseItemDataDto {
    private String name;
    private String articularId;
    private Map<String, String> description;
    private String categoryName;
    private String itemTypeName;
    private String brandName;
    private String itemStatus;
    private PriceDto fullPrice;
    private PriceDto currentPrice;
    private Map<OneFieldEntityDto, Set<OneFieldEntityDto>> optionGroupOptionItemMap;
    private double discountAmount;
}
