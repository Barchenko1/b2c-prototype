package com.b2c.prototype.processor.order.base;

import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;
import com.b2c.prototype.processor.order.ICurrencyCoefficientProcessor;
import com.b2c.prototype.transform.order.IOrderTransformService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CurrencyCoefficientProcessor implements ICurrencyCoefficientProcessor {

    private final ICurrencyCoefficientManager currencyCoefficientManager;
    private final IOrderTransformService orderTransformService;

    public CurrencyCoefficientProcessor(ICurrencyCoefficientManager currencyCoefficientManager, IOrderTransformService orderTransformService) {
        this.currencyCoefficientManager = currencyCoefficientManager;
        this.orderTransformService = orderTransformService;
    }

    @Override
    public void saveCurrencyCoefficient(Map<String, String> requestParams, CurrencyConvertDateDto currencyConvertDateDto) {
        currencyCoefficientManager.saveCurrencyCoefficient(currencyConvertDateDto);
    }

    @Override
    public void updateCurrencyCoefficient(Map<String, String> requestParams, CurrencyConvertDateDto currencyConvertDateDto) {
        LocalDate dateOfCreate = LocalDate.parse(requestParams.get("dateOfCreate"));
        currencyCoefficientManager.updateCurrencyCoefficient(dateOfCreate, currencyConvertDateDto);
    }

    @Override
    public void deleteCurrencyCoefficient(Map<String, String> requestParams) {
        LocalDate dateOfCreate = LocalDate.parse(requestParams.get("dateOfCreate"));
        currencyCoefficientManager.deleteCurrencyCoefficient(dateOfCreate);
    }

    @Override
    public CurrencyConvertDateDto getResponseCurrencyCoefficient(Map<String, String> requestParams) {
        LocalDate dateOfCreate = LocalDate.parse(requestParams.get("dateOfCreate"));
        return Optional.of(currencyCoefficientManager.getCurrencyCoefficient(dateOfCreate))
                .map(orderTransformService::mapCurrencyCoefficientToCurrencyConvertDateDto)
                .orElseThrow(() -> new RuntimeException(""));
    }

    @Override
    public List<CurrencyConvertDateDto> getResponseCurrencyCoefficientList(Map<String, String> requestParams) {
        return currencyCoefficientManager.getCurrencyCoefficients().stream()
                .map(orderTransformService::mapCurrencyCoefficientToCurrencyConvertDateDto)
                .toList();
    }
}
