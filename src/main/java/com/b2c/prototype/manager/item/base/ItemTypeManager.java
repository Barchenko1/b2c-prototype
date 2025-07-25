package com.b2c.prototype.manager.item.base;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.transform.function.ITransformationFunctionService;
import com.b2c.prototype.manager.AbstractConstantEntityManager;
import com.b2c.prototype.manager.item.IItemTypeManager;
import com.tm.core.finder.factory.IParameterFactory;
import com.tm.core.process.dao.common.ITransactionEntityDao;

public class ItemTypeManager extends AbstractConstantEntityManager<ConstantPayloadDto, ItemType> implements IItemTypeManager {

    public ItemTypeManager(IParameterFactory parameterFactory,
                           ITransactionEntityDao itemTypeDao,
                           ITransformationFunctionService transformationFunctionService) {
        super(parameterFactory, itemTypeDao,
                new String[] {"ItemType.findByValue", "ItemType.all"},
                transformationFunctionService.getTransformationFunction(ConstantPayloadDto.class, ItemType.class),
                transformationFunctionService.getTransformationFunction(ItemType.class, ConstantPayloadDto.class));
    }
}
