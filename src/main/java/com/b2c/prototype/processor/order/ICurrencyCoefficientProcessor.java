package com.b2c.prototype.processor.order;

import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCurrencyCoefficientDto;

import java.util.List;
import java.util.Map;

public interface ICurrencyCoefficientProcessor {
    void saveCurrencyCoefficient(Map<String, String> requestParams, List<CurrencyConvertDto> currencyConvertDtoList);
    void deleteCurrencyCoefficient(Map<String, String> requestParams, List<CurrencyConvertDateDto> currencyConvertDateDtoList);

    ResponseCurrencyCoefficientDto getResponseCurrencyCoefficient(Map<String, String> requestParams);
    List<ResponseCurrencyCoefficientDto> getResponseCurrencyCoefficientList(Map<String, String> requestParams);
}
