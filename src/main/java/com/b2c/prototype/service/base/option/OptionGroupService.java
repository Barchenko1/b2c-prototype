package com.b2c.prototype.service.base.option;

import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import com.b2c.prototype.dao.cashed.IEntityStringMapWrapper;
import com.b2c.prototype.service.single.AbstractSingleEntityService;
import com.tm.core.dao.single.ISingleEntityDao;
import com.tm.core.processor.finder.parameter.Parameter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OptionGroupService extends AbstractSingleEntityService implements IOptionGroupService {

    private final IOptionGroupDao optionGroupDao;
    private final IEntityStringMapWrapper<OptionGroup> optionGroupEntityMapWrapper;

    public OptionGroupService(IOptionGroupDao optionGroupDao,
                                          IEntityStringMapWrapper<OptionGroup> optionGroupEntityMapWrapper) {
        this.optionGroupDao = optionGroupDao;
        this.optionGroupEntityMapWrapper = optionGroupEntityMapWrapper;
    }

    @Override
    protected ISingleEntityDao getEntityDao() {
        return this.optionGroupDao;
    }

    @Override
    public void saveOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        OptionGroup optionGroup = OptionGroup.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        super.saveEntity(optionGroup);
        optionGroupEntityMapWrapper.putEntity(requestOneFieldEntityDto.getRequestValue(), optionGroup);
    }

    @Override
    public void updateOptionGroup(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        OptionGroup optionGroup = OptionGroup.builder()
                .name(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                .build();

        Parameter parameter =
                parameterFactory.createStringParameter("name", requestOneFieldEntityDtoUpdate.getOldEntityDto().getRequestValue());
        super.updateEntity(optionGroup, parameter);
        optionGroupEntityMapWrapper.updateEntity(
                requestOneFieldEntityDtoUpdate.getOldEntityDto().getRequestValue(),
                requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue(),
                optionGroup);
    }

    @Override
    public void deleteOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        Parameter parameter =
                parameterFactory.createStringParameter("name", requestOneFieldEntityDto.getRequestValue());
        super.deleteEntity(parameter);
        optionGroupEntityMapWrapper.removeEntity(requestOneFieldEntityDto.getRequestValue());
    }

}
