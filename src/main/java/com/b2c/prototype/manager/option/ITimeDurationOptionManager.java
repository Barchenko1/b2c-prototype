package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.group.TimeDurationOptionGroupDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;

import java.util.List;

public interface ITimeDurationOptionManager {
    void persistEntity(TimeDurationOptionGroupDto timeDurationOptionDto);
    void mergeEntity(String value, TimeDurationOptionGroupDto timeDurationOptionGroupDto);
    void removeEntity(String value);
    OptionGroup getEntity(String value);
    List<OptionGroup> getEntities();
}
