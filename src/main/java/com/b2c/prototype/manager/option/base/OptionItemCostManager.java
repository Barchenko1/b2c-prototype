package com.b2c.prototype.manager.option.base;

import com.b2c.prototype.dao.IGeneralEntityDao;
import com.b2c.prototype.manager.option.IOptionItemCostManager;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.query.IQueryService;
import com.tm.core.process.manager.common.operator.EntityOperationManager;
import com.tm.core.process.manager.common.IEntityOperationManager;
import org.springframework.stereotype.Service;

@Service
public class OptionItemCostManager implements IOptionItemCostManager {
    private final IEntityOperationManager entityOperationManager;
    private final ITransformationFunctionService transformationFunctionService;
    private final IParameterFactory parameterFactory;

    public OptionItemCostManager(IGeneralEntityDao optionItemCostDao,
                                 ITransformationFunctionService transformationFunctionService,
                                 IParameterFactory parameterFactory) {
        this.entityOperationManager = new EntityOperationManager(null);
        this.transformationFunctionService = transformationFunctionService;
        this.parameterFactory = parameterFactory;
    }


}
