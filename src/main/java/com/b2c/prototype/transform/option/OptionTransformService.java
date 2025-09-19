package com.b2c.prototype.transform.option;

import com.b2c.prototype.modal.dto.common.ConstantPayloadDto;
import com.b2c.prototype.modal.entity.option.OptionGroup;
import org.springframework.stereotype.Service;

@Service
public class OptionTransformService implements IOptionTransformService {
    @Override
    public OptionGroup mapConstantPayloadDtoToOptionGroup(ConstantPayloadDto constantPayloadDto) {
        return null;
    }

    @Override
    public ConstantPayloadDto mapOptionGroupToConstantPayloadDto(OptionGroup optionGroup) {
        return null;
    }
}
