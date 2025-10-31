package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.group.OptionItemGroupDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;

import java.util.List;
import java.util.Optional;

public interface IOptionItemGroupManager {
    void persistEntity(OptionItemGroupDto optionItemGroupDto);
    void mergeEntity(String searchValue, OptionItemGroupDto optionItemGroupDto);
    void removeEntity(String value);
    OptionGroup getEntity(String value);
    Optional<OptionGroup> getEntityOptional(String value);
    List<OptionGroup> getEntities();
}
