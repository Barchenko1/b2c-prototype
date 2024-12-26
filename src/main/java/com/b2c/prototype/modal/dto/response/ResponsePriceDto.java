package com.b2c.prototype.modal.dto.response;

import com.b2c.prototype.modal.dto.request.PriceDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponsePriceDto {
    private PriceDto fullPrice;
    private PriceDto totalPrice;
}
