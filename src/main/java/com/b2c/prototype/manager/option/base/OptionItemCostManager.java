package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.option.IOptionItemCostDao;
import com.b2c.prototype.manager.option.IOptionItemCostManager;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.query.ISearchService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.manager.common.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;

public class OptionItemCostManager implements IOptionItemCostManager {
    private final IEntityOperationManager entityOperationManager;
    private final ISearchService searchService;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public OptionItemCostManager(IOptionItemCostDao optionItemCostDao, ISearchService searchService, ITransformationFunctionService transformationFunctionService, IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(optionItemCostDao);
        this.searchService = searchService;
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }


}
