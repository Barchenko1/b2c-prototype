package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.option.IOptionItemCostDao;
import com.b2c.prototype.manager.option.IOptionItemCostManager;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.manager.common.operator.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

public class OptionItemCostManager implements IOptionItemCostManager {
    private final IEntityOperationManager entityOperationManager;
    private final IQueryService queryService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public OptionItemCostManager(IOptionItemCostDao optionItemCostDao,
                                 IQueryService queryService, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(optionItemCostDao);
        this.queryService = queryService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }


}
