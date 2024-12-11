package com.b2c.prototype.modal.dto.update;

import com.b2c.prototype.modal.dto.common.AbstractSearchFieldEntityDtoUpdate;
import com.b2c.prototype.modal.dto.request.OptionItemDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class OptionItemDtoUpdate extends AbstractSearchFieldEntityDtoUpdate<OptionItemDto> {
    private String optionItemValue;
}
