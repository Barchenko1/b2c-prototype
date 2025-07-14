package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.manager.payment.ICurrencyCoefficientManager;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDateDto;
import com.b2c.prototype.modal.dto.payload.order.CurrencyConvertDto;
import com.b2c.prototype.modal.dto.payload.order.ResponseCurrencyCoefficientDto;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.dao.IFetchHandler;
import com.tm.core.process.manager.common.ITransactionEntityOperationManager;
import com.tm.core.process.manager.common.operator.TransactionEntityOperationManager;

import java.util.Date;
import java.util.List;

public class CurrencyCoefficientManager implements ICurrencyCoefficientManager {

    private final ITransactionEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final IFetchHandler fetchHandler;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public CurrencyCoefficientManager(ITransactionEntityDao currencyCoefficientDao,
                                      IQueryService queryService, IFetchHandler fetchHandler,
                                      ITransformationFunctionService transformationFunctionService,
                                      IParameterFactory parameterFactory) {
        this.entityOperationManager = new TransactionEntityOperationManager(currencyCoefficientDao);
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
