package com.b2c.prototype.modal.dto.payload.order;

import com.b2c.prototype.modal.dto.payload.constant.CurrencyDto;
import com.b2c.prototype.modal.entity.price.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConvertDateDto {
    private CurrencyDto currencyFrom;
    private CurrencyDto currencyTo;
    private double coefficient;
    private LocalDate dateOfCreate;
}
