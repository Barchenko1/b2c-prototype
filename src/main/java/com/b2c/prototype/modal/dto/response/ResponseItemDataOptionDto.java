package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class ResponseItemDataOptionDto {
    private String articularId;
    private long dateOfCreate;
    private PriceDto fullPrice;
    private PriceDto currentPrice;
    private Map<OneFieldEntityDto, Set<OneFieldEntityDto>> optionGroupOptionItemMap;
    private PriceDto discountPrice;
}
