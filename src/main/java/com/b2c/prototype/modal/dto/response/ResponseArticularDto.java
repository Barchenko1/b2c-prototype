package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.dto.payload.DiscountDto;
import com.b2c.prototype.modal.dto.payload.InitDiscountDto;
import com.b2c.prototype.modal.dto.payload.OptionGroupOptionItemSetDto;
import com.b2c.prototype.modal.dto.payload.PriceDto;
import com.b2c.prototype.modal.dto.payload.SingleOptionItemDto;
import com.b2c.prototype.modal.entity.item.ArticularStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseArticularDto {
    private String articularId;
    private String productName;
    private Set<OptionGroupOptionItemSetDto> options;
    private PriceDto fullPrice;
    private PriceDto totalPrice;
    private ConstantPayloadDto status;
    private DiscountDto discount;
}
