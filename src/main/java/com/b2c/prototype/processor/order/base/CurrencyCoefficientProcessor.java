package com.b2c.prototype.processor.order.base;

import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCurrencyCoefficientDto;
import com.b2c.prototype.processor.order.ICurrencyCoefficientProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CurrencyCoefficientProcessor implements ICurrencyCoefficientProcessor {

    private final ICurrencyCoefficientManager currencyCoefficientManager;

    public CurrencyCoefficientProcessor(ICurrencyCoefficientManager currencyCoefficientManager) {
        this.currencyCoefficientManager = currencyCoefficientManager;
    }

    @Override
    public void saveCurrencyCoefficient(Map<String, String> requestParams, List<CurrencyConvertDto> currencyConvertDtoList) {
        currencyCoefficientManager.saveCurrencyCoefficient(currencyConvertDtoList);
    }

    @Override
    public void deleteCurrencyCoefficient(Map<String, String> requestParams, List<CurrencyConvertDateDto> currencyConvertDateDtoList) {
        currencyCoefficientManager.deleteCurrencyCoefficient(currencyConvertDateDtoList);
    }

    @Override
    public ResponseCurrencyCoefficientDto getResponseCurrencyCoefficient(Map<String, String> requestParams) {
        return null;
    }

    @Override
    public List<ResponseCurrencyCoefficientDto> getResponseCurrencyCoefficientList(Map<String, String> requestParams) {
        return List.of();
    }
}
