package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;

import java.util.List;

public interface IOptionItemCostGroupProcess {
    void persistEntity(final OptionItemCostGroupDto payload);
    void mergeEntity(final OptionItemCostGroupDto payload, final String value);
    void removeEntity(final String value);

    List<OptionItemCostGroupDto> getEntityList(final String location);
    OptionItemCostGroupDto getEntity(final String location, final String value);
}
