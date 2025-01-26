package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractConstantEntityService;
import com.b2c.prototype.service.processor.item.IItemTypeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class ItemTypeService extends AbstractConstantEntityService<ConstantPayloadDto, ItemType> implements IItemTypeService {

    public ItemTypeService(IParameterFactory parameterFactory,
                           IEntityDao itemTypeDao,
                           ITransformationFunctionService transformationFunctionService,
                           ISingleValueMap singleValueMap) {
        super(parameterFactory, itemTypeDao, singleValueMap,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, ItemType.class),
                transformationFunctionService.getTransformationFunction(ItemType.class, ConstantPayloadDto.class));
    }
}
