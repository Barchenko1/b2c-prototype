package com.b2c.prototype.manager.payment;

import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.entity.payment.CurrencyCoefficient;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ICurrencyCoefficientManager {
    void saveCurrencyCoefficient(CurrencyConvertDateDto currencyConvertDateDto);
    void updateCurrencyCoefficient(LocalDate dateOfCreate, CurrencyConvertDateDto currencyConvertDateDto);
    void deleteCurrencyCoefficient(LocalDate dateOfCreate);

    CurrencyCoefficient getCurrencyCoefficient(LocalDate dateOfCreate);
    List<CurrencyCoefficient> getCurrencyCoefficients();
}
