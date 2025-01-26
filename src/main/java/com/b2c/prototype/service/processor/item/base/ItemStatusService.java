package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.item.IItemStatusService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class ItemStatusService extends AbstractConstantEntityService<ConstantPayloadDto, ItemStatus> implements IItemStatusService {
    public ItemStatusService(IParameterFactory parameterFactory,
                             IEntityDao itemStatusDao,
                             ITransformationFunctionService transformationFunctionService,
                             ISingleValueMap singleValueMap) {
        super(parameterFactory, itemStatusDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, ItemStatus.class),
                transformationFunctionService.getTransformationFunction(ItemStatus.class, ConstantPayloadDto.class));
    }
}
