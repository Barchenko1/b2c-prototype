package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.item.IItemStatusService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class ItemStatusService extends AbstractOneFieldEntityService<ItemStatus> implements IItemStatusService {
    public ItemStatusService(IParameterFactory parameterFactory,
                             IEntityDao itemStatusDao,
                             ITransformationFunctionService transformationFunctionService,
                             ISingleValueMap singleValueMap) {
        super(parameterFactory, itemStatusDao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, ItemStatus> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, ItemStatus.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
