package com.b2c.prototype.service.processor.option.base;

import com.b2c.prototype.dao.cashed.IEntityCachedMap;
import com.b2c.prototype.modal.dto.common.OneFieldEntityDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.service.processor.AbstractOneFieldEntityService;
import com.b2c.prototype.service.processor.option.IOptionGroupService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.factory.IParameterFactory;

import java.util.function.Function;

public class OptionGroupService extends AbstractOneFieldEntityService<OptionGroup> implements IOptionGroupService {

    public OptionGroupService(IParameterFactory parameterFactory,
                              ISingleEntityDao optionGroupDao,
                              IEntityCachedMap entityCachedMap) {
        super(parameterFactory, optionGroupDao, entityCachedMap);
    }

    @Override
    protected Function<OneFieldEntityDto, OptionGroup> getFunction() {
        return oneFieldEntityDto -> OptionGroup.builder()
                .value(oneFieldEntityDto.getValue())
                .build();
    }

    @Override
    protected String getFieldName() {
        return "value";
    }
}
