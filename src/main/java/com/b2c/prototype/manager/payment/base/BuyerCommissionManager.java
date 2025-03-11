package com.b2c.prototype.manager.payment.base;

import com.b2c.prototype.dao.payment.IBuyerCommissionDao;
import com.b2c.prototype.manager.payment.IBuyerCommissionManager;
import com.b2c.prototype.modal.constant.FeeType;
import com.b2c.prototype.modal.dto.payload.CommissionDto;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

import java.time.LocalDateTime;
import java.util.List;

public class BuyerCommissionManager implements IBuyerCommissionManager {
    private final IEntityOperationManager entityOperationManager;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;


    public BuyerCommissionManager(IBuyerCommissionDao buyerCommissionDao, ISearchService searchService, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(buyerCommissionDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }

    @Override
    public void putBuyerCommission(CommissionDto commissionDto) {

    }

    @Override
    public void deleteBuyerCommission(LocalDateTime effectiveDate) {

    }

    @Override
    public List<CommissionDto> getBuyerCommissions() {
        return List.of();
    }
}
