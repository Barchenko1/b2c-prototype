package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCurrencyCoefficientDto;

import java.util.Date;
import java.util.List;

public interface ICurrencyCoefficientManager {
    void saveCurrencyCoefficient(List<CurrencyConvertDto> currencyConvertDtoSet);
    void deleteCurrencyCoefficient(List<CurrencyConvertDateDto> currencyConvertDateDtoSet);

    ResponseCurrencyCoefficientDto getCurrencyCoefficient(CurrencyConvertDateDto currencyConvertDateDto);
    List<ResponseCurrencyCoefficientDto> getCurrencyCoefficients(Date dateOfCreate);
}
