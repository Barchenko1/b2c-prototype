package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemType;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.item.IItemTypeService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class ItemTypeService extends AbstractOneFieldEntityService<ItemType> implements IItemTypeService {

    public ItemTypeService(IParameterFactory parameterFactory,
                           ISingleEntityDao itemTypeDao,
                           IEntityCachedMap entityCachedMap) {
        super(parameterFactory, itemTypeDao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, ItemType> getFunction() {
        return oneFieldEntityDto -> ItemType.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
