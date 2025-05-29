package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.ICurrencyCoefficientDao;
import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCurrencyCoefficientDto;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.identifier.IQueryService;
import com.tm.core.process.dao.query.IFetchHandler;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CurrencyCoefficientManager implements ICurrencyCoefficientManager {

    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public CurrencyCoefficientManager(ICurrencyCoefficientDao currencyCoefficientDao,
                                      IQueryService queryService, IFetchHandler fetchHandler,
                                      ITransformationFunctionService transformationFunctionService,
                                      IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(currencyCoefficientDao);
        this.queryService = queryService;
        this.fetchHandler = fetchHandler;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
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
