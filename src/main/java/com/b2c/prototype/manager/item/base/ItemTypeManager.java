package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.service.scope.IConstantsScope;
import com.b2c.prototype.modal.dto.payload.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

public class ItemTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, ItemType> implements IItemTypeManager {

    public ItemTypeManager(IParameterFactory parameterFactory,
                           IEntityDao itemTypeDao,
                           ITransformationFunctionService transformationFunctionService,
                           IConstantsScope constantsScope) {
        super(parameterFactory, itemTypeDao, constantsScope,
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, ItemType.class),
                transformationFunctionService.getTransformationFunction(ItemType.class, ConstantPayloadDto.class));
    }
}
