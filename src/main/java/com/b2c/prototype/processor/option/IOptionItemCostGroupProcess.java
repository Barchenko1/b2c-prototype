package com.b2c.prototype.processor.option;

import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;

import java.util.List;
import java.util.Map;

public interface IOptionItemCostGroupProcess {
    void persistEntity(final Map<String, Object> payload);
    void mergeEntity(final Map<String, Object> payload, final String value);
    void removeEntity(final String value);

    List<OptionItemCostGroupDto> getEntityList(final String location);
    OptionItemCostGroupDto getEntity(final String location, final String value);
}
