package com.b2c.prototype.service.client.option;

import com.b2c.prototype.dao.option.IOptionGroupDao;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDto;
import com.b2c.prototype.modal.client.dto.common.RequestOneFieldEntityDtoUpdate;
import com.b2c.prototype.modal.client.entity.option.OptionGroup;
import com.b2c.prototype.processor.IEntityStringMapWrapper;
import com.b2c.prototype.service.client.AbstractService;
import com.b2c.prototype.service.client.RequestEntityWrapper;
import lombok.extern.slf4j.Slf4j;

import static com.b2c.prototype.util.Query.DELETE_OPTION_GROUP_BY_NAME;
import static com.b2c.prototype.util.Query.UPDATE_OPTION_GROUP_BY_NAME;

@Slf4j
public class OptionGroupService extends AbstractService implements IOptionGroupService {

    private final IOptionGroupDao optionGroupDao;
    private final IEntityStringMapWrapper<OptionGroup> optionGroupEntityMapWrapper;

    public OptionGroupService(IOptionGroupDao optionGroupDao,
                              IEntityStringMapWrapper<OptionGroup> optionGroupEntityMapWrapper) {
        this.optionGroupDao = optionGroupDao;
        this.optionGroupEntityMapWrapper = optionGroupEntityMapWrapper;
    }

    @Override
    public void saveOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        OptionGroup optionGroup = OptionGroup.builder()
                .name(requestOneFieldEntityDto.getRequestValue())
                .build();

        RequestEntityWrapper<OptionGroup> requestEntityWrapper = new RequestEntityWrapper<>(
                optionGroupDao,
                requestOneFieldEntityDto,
                optionGroup,
                optionGroupEntityMapWrapper
        );
        super.saveEntity(requestEntityWrapper);
    }

    @Override
    public void updateOptionGroup(RequestOneFieldEntityDtoUpdate requestOneFieldEntityDtoUpdate) {
        OptionGroup optionGroup = OptionGroup.builder()
                .name(requestOneFieldEntityDtoUpdate.getNewEntityDto().getRequestValue())
                .build();

        RequestEntityWrapper<OptionGroup> requestEntityWrapper = new RequestEntityWrapper<>(
                UPDATE_OPTION_GROUP_BY_NAME,
                optionGroupDao,
                requestOneFieldEntityDtoUpdate,
                optionGroup,
                optionGroupEntityMapWrapper
        );
        super.updateEntity(requestEntityWrapper);
    }

    @Override
    public void deleteOptionGroup(RequestOneFieldEntityDto requestOneFieldEntityDto) {
        RequestEntityWrapper<OptionGroup> requestEntityWrapper = new RequestEntityWrapper<>(
                DELETE_OPTION_GROUP_BY_NAME,
                optionGroupDao,
                requestOneFieldEntityDto,
                optionGroupEntityMapWrapper
        );
        super.deleteEntity(requestEntityWrapper);
    }

}
