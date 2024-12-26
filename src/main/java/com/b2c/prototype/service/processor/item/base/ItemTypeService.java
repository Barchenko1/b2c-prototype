package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.ISingleValueMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.service.function.ITransformationFunctionService;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.item.IItemTypeService;
import com.tm.core.dao.common.IEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class ItemTypeService extends AbstractOneFieldEntityService<ItemType> implements IItemTypeService {

    public ItemTypeService(IParameterFactory parameterFactory,
                           IEntityDao itemTypeDao,
                           ITransformationFunctionService transformationFunctionService,
                           ISingleValueMap singleValueMap) {
        super(parameterFactory, itemTypeDao, transformationFunctionService, singleValueMap);
    }

    @Override
    protected Function<OneFieldEntityDto, ItemType> getFunction() {
        return transformationFunctionService.getTransformationFunction(OneFieldEntityDto.class, ItemType.class);
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
