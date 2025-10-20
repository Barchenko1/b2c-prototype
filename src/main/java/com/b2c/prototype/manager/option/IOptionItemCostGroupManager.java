package com.b2c.prototype.manager.option;

import com.b2c.prototype.modal.dto.payload.option.group.OptionItemCostGroupDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;

import java.util.List;
import java.util.Optional;

public interface IOptionItemCostGroupManager {
    void persistEntity(OptionItemCostGroupDto optionItemCostGroupDto);
    void mergeEntity(String searchValue, OptionItemCostGroupDto optionItemCostGroupDto);
    void removeEntity(String value);
    OptionGroup getEntity(String value);
    Optional<OptionGroup> getEntityOptional(String value);
    List<OptionGroup> getEntities();
}
