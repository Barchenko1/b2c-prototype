package com.b2c.prototype.service.processor.item.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.dao.item.IItemStatusDao;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.item.ItemStatus;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.item.IItemStatusService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class ItemStatusService extends AbstractOneFieldEntityService<ItemStatus> implements IItemStatusService {
    public ItemStatusService(IParameterFactory parameterFactory,
                             ISingleEntityDao itemStatusDao,
                             IEntityCachedMap entityCachedMap) {
        super(parameterFactory, itemStatusDao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, ItemStatus> getFunction() {
        return oneFieldEntityDto -> ItemStatus.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
