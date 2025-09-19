package com.b2c.prototype.transform.option;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;

public interface IOptionTransformService {
    OptionGroup mapConstantPayloadDtoToOptionGroup(ConstantPayloadDto constantPayloadDto);
    ConstantPayloadDto mapOptionGroupToConstantPayloadDto(OptionGroup optionGroup);
}
