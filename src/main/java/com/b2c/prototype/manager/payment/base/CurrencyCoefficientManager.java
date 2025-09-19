package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCurrencyCoefficientDto;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CurrencyCoefficientManager implements ICurrencyCoefficientManager {

    private final IGeneralEntityDao generalEntityDao;

    public CurrencyCoefficientManager(IGeneralEntityDao generalEntityDao) {
        this.generalEntityDao = generalEntityDao;
    }

    @Override
    public void saveCurrencyCoefficient(List<CurrencyConvertDto> currencyConvertDtoSet) {

    }

    @Override
    public void deleteCurrencyCoefficient(List<CurrencyConvertDateDto> currencyConvertDateDtoSet) {

    }

    @Override
    public ResponseCurrencyCoefficientDto getCurrencyCoefficient(CurrencyConvertDateDto currencyConvertDateDto) {
        return null;
    }

    @Override
    public List<ResponseCurrencyCoefficientDto> getCurrencyCoefficients(Date dateOfCreate) {
        return List.of();
    }
}
