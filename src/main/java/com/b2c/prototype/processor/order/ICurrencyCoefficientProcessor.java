package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;

import java.util.List;
import java.util.Map;

public interface ICurrencyCoefficientProcessor {
    void saveCurrencyCoefficient(Map<String, String> requestParams, CurrencyConvertDateDto currencyConvertDateDto);
    void updateCurrencyCoefficient(Map<String, String> requestParams, CurrencyConvertDateDto currencyConvertDateDto);
    void deleteCurrencyCoefficient(Map<String, String> requestParams);

    CurrencyConvertDateDto getResponseCurrencyCoefficient(Map<String, String> requestParams);
    List<CurrencyConvertDateDto> getResponseCurrencyCoefficientList(Map<String, String> requestParams);
}
