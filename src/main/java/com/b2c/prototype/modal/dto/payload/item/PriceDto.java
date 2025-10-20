package com.b2c.prototype.modal.dto.payload.item;

import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceDto {
    private Double amount;
    private CurrencyDto currency;
}
